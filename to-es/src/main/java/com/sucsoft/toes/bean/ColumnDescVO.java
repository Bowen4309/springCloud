package com.sucsoft.toes.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Libin
 */
@ApiModel("字段注释")
public class ColumnDescVO {

    @ApiModelProperty("id")
    private String id;

    @ApiModelProperty("字段名")
    private String columnName;

    @ApiModelProperty("字段解释")
    private String columnDesc;

    @ApiModelProperty("索引名")
    private String indexName;

    public String getId() {
        return id;
    }

    public ColumnDescVO setId(String id) {
        this.id = id;
        return this;
    }

    public String getColumnName() {
        return columnName;
    }

    public ColumnDescVO setColumnName(String columnName) {
        this.columnName = columnName;
        return this;
    }

    public String getColumnDesc() {
        return columnDesc;
    }

    public ColumnDescVO setColumnDesc(String columnDesc) {
        this.columnDesc = columnDesc;
        return this;
    }

    public String getIndexName() {
        return indexName;
    }

    public ColumnDescVO setIndexName(String indexName) {
        this.indexName = indexName;
        return this;
    }
}
