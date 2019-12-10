package org.thraex.fs.base.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.thraex.fs.base.entity.FileInfo;
import org.thraex.fs.base.service.FileService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

/**
 * @author 鬼王
 * @date 2019/12/01 15:56
 */
@RestController
@RequestMapping("file")
public class FileController {

    @Autowired
    private FileService fileService;

    @PostMapping
    public FileInfo upload(@RequestParam MultipartFile file, String app) {
        return fileService.save(file, app);
    }

    @PostMapping("multiple")
    public List<FileInfo> multiple(@RequestParam List<MultipartFile> files, String app) {
        return fileService.save(files, app);
    }

    @GetMapping
    public List<FileInfo> list() {
        return fileService.list();
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable String id) {
        fileService.delete(id);
    }

    @DeleteMapping("clear")
    public void clear() {
        fileService.clear();
    }

    @GetMapping(value = { "download/{id}", "download/{id}/{name}"})
    public void download(
            @PathVariable String id,
            @PathVariable(required = false) String name,
            HttpServletResponse response) {
        Optional.ofNullable(fileService.find(id)).ifPresent(info -> {
            String filename = Optional.ofNullable(name)
                    .map(it -> it.trim())
                    .map(it -> it.length() > 0 ? it : null)
                    .orElse(info.getName());

            Path path = Paths.get(info.getPath());
            final String encoding = "UTF-8";
            try {
                response.reset();
                System.out.println(Files.probeContentType(path));
                response.setContentType(info.getContentType());
                response.setCharacterEncoding(encoding);
                response.addHeader("Content-Disposition", "attachment;filename=" +
                        new String(filename.getBytes(encoding), "ISO-8859-1"));
                // URLEncoder.encode(filename, "UTF-8")
                response.addHeader("Accept-Ranges", "bytes");

                Files.copy(path, response.getOutputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    @RequestMapping(
            value = { "archive", "archive/{name}" },
            method = { RequestMethod.GET, RequestMethod.POST })
    public void archive(@PathVariable(required = false) String name) {
        System.out.println(name);
    }

}
