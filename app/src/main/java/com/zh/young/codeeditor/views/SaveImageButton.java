package com.zh.young.codeeditor.views;

import android.content.Context;
import android.util.AttributeSet;


public class SaveImageButton extends AbstractImageButton {

    public SaveImageButton(Context context) {
        this(context,null);
    }

    public SaveImageButton(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public SaveImageButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void unUsable() {

    }

    @Override
    public void usable() {

    }
}
