package com.zh.young.codeeditor.Command;

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
        EditText editText = (EditText) view;
        editText.setText(data);
        editText.setSelection(data.length());


    }
}
