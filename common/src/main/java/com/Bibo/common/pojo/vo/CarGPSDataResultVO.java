package com.Bibo.common.pojo.vo;

import java.util.List;

public class CarGPSDataResultVO {

    private List<CarGPSDataListResultVO> list;

    private Integer total;

    private Integer code;

    private String message;

    public List<CarGPSDataListResultVO> getList() {
        return list;
    }

    public void setList(List<CarGPSDataListResultVO> list) {
        this.list = list;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
