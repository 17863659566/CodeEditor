package com.zh.young.codeeditor.Exception;

/**
 * 文件系统未挂载造成的错误
 */

public class FileSystemNotMount extends RuntimeException {
    public static final long serialVersionUID = 12L;
    public FileSystemNotMount(){

    }
    public FileSystemNotMount(String message) {
        super(message);
    }

    public FileSystemNotMount(String message, Exception e) {
        super(message, e);
    }

    public FileSystemNotMount(Exception e) {
        super(e);
    }
}
