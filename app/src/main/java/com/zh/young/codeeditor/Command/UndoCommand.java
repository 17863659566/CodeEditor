package com.zh.young.codeeditor.Command;

import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.zh.young.codeeditor.entity.MementoEntity;
import com.zh.young.codeeditor.states.Memento;

/**
 * 回退命令
 */

public class UndoCommand extends Command{
    @Override
    void execute(View view) {
        Memento instance = Memento.getInstance();
        MementoEntity entity = instance.undoGetState();
        if(entity == null){
            return;
        }
        String data = entity.getData();
        int start = entity.getStart();
        int end = entity.getAfter();
        EditText editText = (EditText) view;
        Log.i("data","data = " + data + "-----"+ start + "----" +(start+end) + data.substring(start,start+end));
        editText.setText(data.substring(start,end));
        editText.setSelection(end-start);


    }
}
