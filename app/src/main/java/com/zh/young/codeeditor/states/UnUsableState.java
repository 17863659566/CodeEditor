package com.zh.young.codeeditor.states;

/**
 * 不可用状态类，用于在编辑界面或其他界面的状态切换
 */
public class UnUsableState implements State {
    /**
     * 返回不可用
     * @return false
     */

    @Override
    public boolean isUsable() {
        return false;
    }
}
