package com.zh.young.codeeditor.views;

import android.content.Context;
import android.util.AttributeSet;


public class RedoImageButton extends AbstractImageButton {

    public RedoImageButton(Context context) {
        this(context,null);
    }

    public RedoImageButton(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public RedoImageButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void unUsable() {

    }

    @Override
    public void usable() {

    }
}
