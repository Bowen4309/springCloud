package com.sucsoft.toes.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Libin
 */
@ApiModel("简易字段注释VO")
public class SimpleColumnDescVO {

    @ApiModelProperty("字段名")
    private String columnName;

    @ApiModelProperty("字段解释")
    private String columnDesc;

    public String getColumnName() {
        return columnName;
    }

    public SimpleColumnDescVO setColumnName(String columnName) {
        this.columnName = columnName;
        return this;
    }

    public String getColumnDesc() {
        return columnDesc;
    }

    public SimpleColumnDescVO setColumnDesc(String columnDesc) {
        this.columnDesc = columnDesc;
        return this;
    }
}
