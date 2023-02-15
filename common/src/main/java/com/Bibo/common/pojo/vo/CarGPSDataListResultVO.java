package com.Bibo.common.pojo.vo;

import lombok.Data;

import java.util.Date;

@Data
public class CarGPSDataListResultVO {

    private String address;

    private String bustype;

    private CarGPSDataListFlogshowResultVO flogshow;

    private String idcardno;

    private String location;

    private String pointid;

    private Date time;

    private Boolean unit;

    //采集时间
    private Date collecttime;

    //修改时间
    private Date updateTime;

}
