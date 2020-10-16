package org.thraex.fs.svg.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author 鬼王
 * @Date 2019/08/07 17:29
 */
@Data
public class SvgInfo implements Serializable {

    private String id;

    private String name;

    private String content;

    public SvgInfo() { }

    public SvgInfo(String id, String name, String content) {
        this.id = id;
        this.name = name;
        this.content = content;
    }
}
