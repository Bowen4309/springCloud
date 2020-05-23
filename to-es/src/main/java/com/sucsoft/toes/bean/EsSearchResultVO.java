package com.sucsoft.toes.bean;

/**
 * <br>-lastModify:2019/8/21 18:57
 *
 * @author Lixiaoban
 * @version 1.0
 */
public class EsSearchResultVO {

    private boolean acknowledged;

    private boolean shards_acknowledged;

    private String index;

    public boolean isAcknowledged() {
        return acknowledged;
    }

    public EsSearchResultVO setAcknowledged(boolean acknowledged) {
        this.acknowledged = acknowledged;
        return this;
    }

    public boolean isShards_acknowledged() {
        return shards_acknowledged;
    }

    public EsSearchResultVO setShards_acknowledged(boolean shards_acknowledged) {
        this.shards_acknowledged = shards_acknowledged;
        return this;
    }

    public String getIndex() {
        return index;
    }

    public EsSearchResultVO setIndex(String index) {
        this.index = index;
        return this;
    }
}
