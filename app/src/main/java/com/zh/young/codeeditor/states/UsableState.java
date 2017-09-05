package com.zh.young.codeeditor.states;


/**
 * 可用状态类，用于在编辑界面或其他界面的状态切换
 */
public class UsableState implements State {
    /**
     * 可用状态
     * @return 可用
     */
    @Override
    public boolean isUsable() {
        return true;
    }
}
