package com.zh.young.codeeditor.views;

import android.content.Context;
import android.util.AttributeSet;


public class EditImageButton extends AbstractImageButton {
    public EditImageButton(Context context) {
        this(context,null);
    }

    public EditImageButton(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public EditImageButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void unUsable() {

    }

    @Override
    public void usable() {

    }


}
