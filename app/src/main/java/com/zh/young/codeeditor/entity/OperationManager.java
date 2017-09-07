package com.zh.young.codeeditor.entity;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import java.util.LinkedList;

public class OperationManager implements TextWatcher {

    private boolean enable = true;
    private MementoEntity entity;
    private LinkedList undoOPt = new LinkedList();
    private LinkedList redoOpt = new LinkedList();

    private boolean enable(){
        enable = true;
        return enable;
    }
    private boolean disable(){
        enable = false;
        return enable;
    }
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        Log.i("beforeTextChanged","start = " + start + " count = " + count + " after = " + after);
        int end = start + count;
        if(count > 0){
            if(enable){
                if(entity == null){
                    entity = new MementoEntity();

                }
                entity.setSrc(s.subSequence(start,end),start,end);
            }
        }
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        Log.i("onTextChanged","start = " + start + " count = " + count + " before = " + before);
        int end = start + count;
        if(count > 0 ){
            if(enable){
                if(entity == null){
                    entity = new MementoEntity();
                }
                entity.setDst(s.subSequence(start,end),start,end);
            }
        }
    }

    @Override
    public void afterTextChanged(Editable s) {
        if(enable && entity != null){
            if(!redoOpt.isEmpty()){
                redoOpt.clear();
            }

            undoOPt.push(entity);
        }

        entity = null;
    }

    public boolean undo(EditText editText) {
        if(undoOPt.size() <= 0){
            return false;
        }
        MementoEntity undoOpt = (MementoEntity) undoOPt.pop();
        //屏蔽撤销产生的事件
        disable();
        undoOpt.undo(editText);
        enable();



        //填入重做栈
        redoOpt.push(undoOpt);
        return true;

    }

    public boolean redo(EditText editText) {
        if(redoOpt.size() <= 0 ){
            return false;
        }
        MementoEntity redoOp = (MementoEntity) redoOpt.pop();

        //屏蔽重做产生的事件
        disable();
        redoOp.redo(editText);
        enable();

        //填入撤销
        undoOPt.push(redoOp);
        return true;

    }
}
