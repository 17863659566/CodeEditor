package com.zh.young.codeeditor.structures;

import com.zh.young.codeeditor.entity.MementoEntity;


/**
 * 保存状态的数据结构
 */

public class StateDoubleLinkList {

    //作为双向链表的头节点
    private final StateStructure mHead;
    //作为临时的访问引用
    private StateStructure mTemp;
    //记录内部已经有的结构的个数
    private int mCount;

    /**
     * 链表节点的数据结构
     */
    private class StateStructure {
        StateStructure(MementoEntity data) {
            this.data = data;
        }

        MementoEntity data;
        StateStructure pre;
        StateStructure next;
    }

    public StateDoubleLinkList() {
        mHead = new StateStructure(null);
        mHead.pre = mHead.next = null;
        mTemp = mHead;
    }

    /**
     * 将Memento插入到结构中
     *
     * @param entity 需要添加的实体
     */
    public void add(MementoEntity entity) {
        StateStructure next = new StateStructure(entity);
        next.next = mTemp.next;
        next.pre = mTemp;
        mTemp.next = next;
        mTemp = next;

        if (mCount < 5) {
            mCount++;
        } else {
            delete();
        }

    }

    /**
     * 将多余的备忘录对象删除
     */
    private void delete() {
        StateStructure temp = mHead.next;
        temp.next.pre = mHead;
        mHead.next = temp.next;

    }

    /**
     * 获取最近的备忘录对象
     *
     * @return 备忘录对象
     */
    public MementoEntity get() {
        if (mHead.next != null) {
            mTemp.pre = mTemp.next;
            mCount--;
            return mTemp.data;
        } else {
            return null;
        }

    }
}
