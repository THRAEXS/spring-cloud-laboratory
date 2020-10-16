package org.thraex.base.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author 鬼王
 * @date 2019/11/29 15:56
 */
public class BaseEntity implements Serializable {

    protected String id;

    protected String createBy;

    protected LocalDateTime createTime;

    protected String updateBy;

    protected LocalDateTime updateTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }
}
