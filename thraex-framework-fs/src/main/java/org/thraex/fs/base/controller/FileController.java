package org.thraex.fs.base.controller;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.thraex.base.result.Result;
import org.thraex.fs.base.entity.FileInfo;
import org.thraex.fs.base.service.FileService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

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
            HttpServletResponse response,
            @PathVariable String id,
            @PathVariable(required = false) String name) {
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
    public void archive(HttpServletRequest request,
                        HttpServletResponse response,
                        @PathVariable(required = false) String name,
                        @RequestBody(required = false) List<FileInfo> data,
                        @RequestParam(required = false) List<String> params) {
        Supplier<Optional<Stream<FileInfo>>> conditions = HttpMethod.GET.matches(request.getMethod()) ?
                () -> Optional.ofNullable(params).map(it -> it.parallelStream()
                        .map(s -> s.split(","))
                        .map(s -> new FileInfo(s[0], s.length > 1 ? s[1] : null))) :
                () -> Optional.ofNullable(data).map(it -> data.parallelStream());

        try {
            Map<String, FileInfo> maps = Optional.of(
                    conditions.get().orElse(Stream.of()).parallel()
                            .filter(it -> !(it.getId() == null || it.getId().trim().length() == 0))
                            .collect(Collectors.toMap(it -> it.getId().trim(), Function.identity())))
                    .map(it -> it.isEmpty() ? null : it)
                    .orElseThrow(() -> new IllegalArgumentException("Parameter is wrong."));

            List<FileInfo> info = Optional.of(
                    fileService.list(maps.keySet().stream().collect(Collectors.toList()))
                            .parallelStream()
                            .filter(it -> !StringUtils.isEmpty(it.getPath()) &&
                                    Files.exists(Paths.get(it.getPath())))
                            .map(it -> {
                                Optional.ofNullable(maps.get(it.getId()))
                                        .map(FileInfo::getName)
                                        .map(c -> c.trim())
                                        .map(c -> c.length() > 0 ? c + it.getSuffix() : null)
                                        .ifPresent(c -> it.setName(c));
                                return it;
                            }).collect(Collectors.toList()))
                    .map(it -> it.isEmpty() ? null : it)
                    .orElseThrow(() -> new FileNotFoundException("File not found."));

            String tmpName = String.valueOf(LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli());
            File file = Files.createTempFile(tmpName, ".tmp.zip").toFile();

            FileOutputStream fos = new FileOutputStream(file);
            ZipOutputStream zipOut = new ZipOutputStream(fos);
            info.stream().forEach(it -> {
                try {
                    File fileToZip = Paths.get(it.getPath()).toFile();
                    FileInputStream fis = new FileInputStream(fileToZip);
                    ZipEntry zipEntry = new ZipEntry(it.getName());
                    zipOut.putNextEntry(zipEntry);

                    byte[] bytes = new byte[1024];
                    int length;
                    while ((length = fis.read(bytes)) >= 0) {
                        zipOut.write(bytes, 0, length);
                    }
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            zipOut.flush();
            fos.flush();
            zipOut.close();
            fos.close();

            String filename = Optional.ofNullable(name).map(it -> it.trim()).orElse(tmpName) + ".zip";
            response.reset();
            response.setContentType("application/x-zip-compressed;charset=UTF-8");
            response.addHeader("Content-Disposition", "attachment;filename=" +
                    new String(filename.getBytes("UTF-8"), "ISO-8859-1"));

            OutputStream os = response.getOutputStream();
            FileInputStream fis = new FileInputStream(file);
            byte[] bytes = new byte[1024];
            int length;
            while ((length = fis.read(bytes)) >= 0) {
                os.write(bytes, 0, length);
            }
            fis.close();
            os.flush();
            os.close();
        } catch (IllegalArgumentException | IOException e) {
            try (OutputStream os = response.getOutputStream()) {
                response.setContentType("application/json;charset=UTF-8");
                os.write(JSON.toJSONBytes(new Result<>().fail(e.getMessage())));
                os.flush();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

}
