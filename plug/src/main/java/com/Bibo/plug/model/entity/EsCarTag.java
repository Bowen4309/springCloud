package com.Bibo.plug.model.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class EsCarTag {


    private String id;

    private String carNumber;

    private String carNumberTypeCode;

    private String tag;

    private String carNumberColor;

}
