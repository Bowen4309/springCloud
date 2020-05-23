package com.sucsoft.toes.bean;

/**
 * 工作进程VO
 * <br>-lastModify:2019/8/25 20:55
 *
 * @author Lixiaoban
 * @version 1.0
 */
public class WorkProcessVO {

    /**
     * 仅有赋值和读取操作，无增加数据大小等操作，不需要使用原子类进行线程安全处理
     * 保证线程之间可见即可
     */
    private volatile long start = 0;

    /**
     * 仅有赋值和读取操作，无增加数据大小等操作，不需要使用原子类进行线程安全处理
     * 保证线程之间可见即可
     */
    private volatile long end = 0;

    public long getStart() {
        return start;
    }

    public WorkProcessVO setStart(long start) {
        this.start = start;
        return this;
    }

    public long getEnd() {
        return end;
    }

    public WorkProcessVO setEnd(long end) {
        this.end = end;
        return this;
    }
}
