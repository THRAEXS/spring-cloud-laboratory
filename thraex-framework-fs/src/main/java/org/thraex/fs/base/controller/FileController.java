package org.thraex.fs.base.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.thraex.fs.base.entity.FileInfo;
import org.thraex.fs.base.service.FileService;

import java.util.List;

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

    @GetMapping("download")
    public void download() {

    }

    @GetMapping("download/archive")
    public void archive() {

    }

}
