package org.thraex.fs.base.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.thraex.fs.base.entity.FileInfo;

import java.util.Arrays;
import java.util.List;

/**
 * @author 鬼王
 * @date 2019/11/29 16:10
 */
@RestController
@RequestMapping("upload")
public class UploadController {

    @PostMapping
    public FileInfo upload(
            @RequestParam MultipartFile file, String type) {
        System.out.println(file);
        System.out.println(type);
        return new FileInfo();
    }

    @PostMapping("multiple")
    public List<FileInfo> uploads(
            @RequestParam List<MultipartFile> files, String type) {
        System.out.println(files);
        System.out.println(type);
        return Arrays.asList(new FileInfo());
    }

}
