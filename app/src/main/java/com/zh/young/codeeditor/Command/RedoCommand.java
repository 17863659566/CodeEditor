package com.zh.young.codeeditor.Command;

import android.view.View;
import android.widget.EditText;

import com.zh.young.codeeditor.entity.MementoEntity;
import com.zh.young.codeeditor.states.Memento;

/**
 * 恢复命令
 */

public class RedoCommand extends Command {
    @Override
    void execute(View view) {
        Memento instance = Memento.getInstance();
        MementoEntity state = instance.redoGetState();
        if(state == null){
            return;
        }
        String data = state.getData();
        EditText editText = (EditText) view;
        editText.setText(data);
        editText.setSelection(data.length());
    }
}
