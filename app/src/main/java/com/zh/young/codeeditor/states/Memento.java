package com.zh.young.codeeditor.states;

import com.zh.young.codeeditor.entity.MementoEntity;
import com.zh.young.codeeditor.structures.StateDoubleLinkList;

/**
 * 备忘录,这个类不能被new
 */

public  class Memento {
    private static Memento  instance;
    private  StateDoubleLinkList undoList = new StateDoubleLinkList();
    private  StateDoubleLinkList redoList = new StateDoubleLinkList();
    private Memento(){

    }
    public static Memento getInstance(){
        if(instance == null){
            synchronized (Memento.class){
                if(instance == null){
                    instance = new Memento();
                }
            }
        }
        return instance;
    }
    /**
     * 获取状态进行回退操作，并且这个备忘录会被加到Redo列表中
     * @return 最近的一个备忘录
     */
     public MementoEntity undoGetState(){
        MementoEntity memento = undoList.get();
        redoList.add(memento);
        return memento;
    }

    /**
     *
     * 从redo内获取一个状态，进行恢复
     * @return 要恢复的备忘录
     */
    public MementoEntity redoGetState(){
        return redoList.get();
    }

    /**
     * 添加备忘录信息
     * @param Entity 要添加的备忘录信息
     */
    public void undoAddState(MementoEntity Entity){
        undoList.add(Entity);
    }

    /**
     * 恢复所需备忘录
     * @param entity 需要进行恢复的备忘录
     */
    public void redoAddState(MementoEntity entity){
        redoList.add(entity);
    }
}
