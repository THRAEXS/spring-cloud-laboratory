package org.thraex.fs.base.entity;

import lombok.Data;

/**
 * @author 鬼王
 * @date 2019/11/25 21:14
 */
@Data
public class FileInfo {

    private String id;

    private String name;

    private String path;

    private String suffix;

    public FileInfo() { }

    public FileInfo(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public FileInfo(String id, String name, String path, String suffix) {
        this.id = id;
        this.name = name;
        this.path = path;
        this.suffix = suffix;
    }
}
