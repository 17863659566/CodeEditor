package com.zh.young.codeeditor.views;

import android.content.Context;
import android.util.AttributeSet;
import android.support.v7.widget.AppCompatImageButton;

import com.zh.young.codeeditor.states.State;
import com.zh.young.codeeditor.states.UnUsableState;
import com.zh.young.codeeditor.states.UsableState;

public abstract class AbstractImageButton extends AppCompatImageButton {
    private State state;
    public AbstractImageButton(Context context) {
        super(context);
    }

    public AbstractImageButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AbstractImageButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    public State getState() {
        return state;
    }

    private void setState(State state) {
        this.state = state;
    }
    public void changeState(State state){
        if(state.isUsable()){
            setState(new UnUsableState());
        }else{
            setState(new UsableState());
        }
    }

    public abstract void unUsable();

    public abstract void usable();



}
