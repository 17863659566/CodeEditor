package com.zh.young.codeeditor.entity;

/**
 * 记录File History的实体类
 */

public class FileHistory {
    String filename;
    String filepath;
    public FileHistory(String filename,String filepath){
        this.filename = filename;
        this.filepath = filepath;
    }
    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }


}
