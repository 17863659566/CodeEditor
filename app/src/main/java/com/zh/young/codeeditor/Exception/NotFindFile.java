package com.zh.young.codeeditor.Exception;

/**
 * 已经没有文件了
 */

public class NotFindFile extends RuntimeException {
    public NotFindFile(String s) {
        super(s);
    }
}
