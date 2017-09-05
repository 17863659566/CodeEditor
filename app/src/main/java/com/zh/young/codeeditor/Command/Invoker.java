package com.zh.young.codeeditor.Command;

import android.view.View;

/**
 * 调用中间类
 */

public class Invoker {
    private Command command;


    //设值注入
    public void setCommand(Command command) {
        this.command = command;
    }

    //业务方法，用于调用命令类的execute()方法
    public void call(View view) {
        command.execute(view);
    }
}
