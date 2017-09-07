package com.zh.young.codeeditor.entity;

import android.text.Editable;
import android.util.Log;
import android.widget.EditText;

import java.io.Serializable;

/**
 * 备忘录保存的实体
 */

 class MementoEntity implements Serializable{
    //用于记录改变前的数据
    private int srcStart;
    private int srcEnd;
    private String src;

    //用于记录改变后的数据
    private int dstStart;
    private int dstEnd;
    private String dst;

    void setSrc(CharSequence src,int srcStart,int srcEnd){
        this.src = src != null ? src.toString() : "";
        this.srcStart = srcStart;
        this.srcEnd = srcEnd;
    }

    void setDst(CharSequence dst,int dstStart,int dstEnd){
        this.dst = dst != null ? dst.toString() : "";
        this.dstStart = dstStart;
        this.dstEnd = dstEnd;
    }

    void undo(EditText text){
        Editable editable = text.getText();

        if(dst != null){
            editable.delete(dstStart,dstEnd);

        }

    }

    void redo(EditText text) {
        Editable editable = text.getText();
        int idx = -1;

        if (dst != null) {
            editable.insert(dstStart, dst);
            idx = dstStart + dst.length();
        }

        if (idx >= 0) {
            text.setSelection(idx);
        }
    }
}
