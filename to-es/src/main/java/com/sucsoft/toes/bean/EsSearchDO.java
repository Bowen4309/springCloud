package com.sucsoft.toes.bean;

import java.util.Map;

/**
 * <br>-lastModify:2019/8/21 8:58
 *
 * @author Lixiaoban
 * @version 1.0
 */
public class EsSearchDO {

    private Integer took;

    private boolean timed_out;

    private Map _shards;

    private Map hits;

    public Integer getTook() {
        return took;
    }

    public EsSearchDO setTook(Integer took) {
        this.took = took;
        return this;
    }

    public boolean isTimed_out() {
        return timed_out;
    }

    public EsSearchDO setTimed_out(boolean timed_out) {
        this.timed_out = timed_out;
        return this;
    }

    public Map get_shards() {
        return _shards;
    }

    public EsSearchDO set_shards(Map _shards) {
        this._shards = _shards;
        return this;
    }

    public Map getHits() {
        return hits;
    }

    public EsSearchDO setHits(Map hits) {
        this.hits = hits;
        return this;
    }
}
