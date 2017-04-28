package com.jaqen.roseshadow.models.bean;

import org.litepal.annotation.Column;
import org.litepal.crud.DataSupport;

import java.util.Date;

/**
 * @author
 * @version 2017-04-26 14:58
 */

public class XinWord extends DataSupport{
    private String word;
    @Column(nullable = false)
    private Date createTime;
    private Date editTime;
    private boolean isShowingStart;

    public Date getCreateTime() {
        return createTime;
    }

    public String getWord() {
        return word;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public Date getEditTime() {
        return editTime;
    }

    public void setEditTime(Date editTime) {
        this.editTime = editTime;
    }

    public boolean isShowingStart() {
        return isShowingStart;
    }

    public void setShowingStart(boolean showingStart) {
        isShowingStart = showingStart;
    }

    @Override
    public long getBaseObjId() {
        return super.getBaseObjId();
    }
}
