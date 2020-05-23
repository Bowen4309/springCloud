package com.sucsoft.toes.bean;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * <br>-lastModify:2019/8/22 20:44
 * sql语句至elk的绑定操作集
 * @author Lixiaoban
 * @version 1.0
 */
@ApiModel("sql语句至elk绑定操作集")
public class SqlStatementToElkDO {

    public static final Integer STATUS_OPEN = 1;

    public static final Integer STATUS_CLOSE = 0;

    @ApiModelProperty("sql")
    private String statement;

    @ApiModelProperty("id")
    private String id;

    @ApiModelProperty("cron表达式,暂时无效")
    private String cron;

    @ApiModelProperty(value = "间隔秒",example = "10000L")
    private Long intervalSeconds;

    @ApiModelProperty("elk索引名称")
    private String indexName;

    @ApiModelProperty("返回的map携带的主键")
    private String idName;

    @ApiModelProperty("修改标识字段")
    private String modifyColumn;

    @ApiModelProperty("规则创建时间")
    private Date createTime;

    @ApiModelProperty(value = "规则执行状态",example = "0")
    private Integer status;

    @ApiModelProperty("indexMapping")
    private String indexMapping;

    public String getIndexMapping() {
        return indexMapping;
    }

    public SqlStatementToElkDO setIndexMapping(String indexMapping) {
        this.indexMapping = indexMapping;
        return this;
    }

    @Override
    public String toString() {
        return "SqlStatementToElkDO{" +
                "statement='" + statement + '\'' +
                ", id='" + id + '\'' +
                ", cron='" + cron + '\'' +
                ", intervalSeconds=" + intervalSeconds +
                ", indexName='" + indexName + '\'' +
                ", idName='" + idName + '\'' +
                ", modifyColumn='" + modifyColumn + '\'' +
                ", createTime=" + createTime +
                ", status=" + status +
                '}';
    }

    public Long getIntervalSeconds() {
        return intervalSeconds;
    }

    public SqlStatementToElkDO setIntervalSeconds(Long intervalSeconds) {
        this.intervalSeconds = intervalSeconds;
        return this;
    }

    public String getStatement() {
        return statement;
    }

    public SqlStatementToElkDO setStatement(String statement) {
        this.statement = statement;
        return this;
    }

    public String getId() {
        return id;
    }

    public SqlStatementToElkDO setId(String id) {
        this.id = id;
        return this;
    }

    public String getCron() {
        return cron;
    }

    public SqlStatementToElkDO setCron(String cron) {
        this.cron = cron;
        return this;
    }

    public String getIndexName() {
        return indexName;
    }

    public SqlStatementToElkDO setIndexName(String indexName) {
        this.indexName = indexName;
        return this;
    }

    public String getIdName() {
        return idName;
    }

    public SqlStatementToElkDO setIdName(String idName) {
        this.idName = idName;
        return this;
    }

    public String getModifyColumn() {
        return modifyColumn;
    }

    public SqlStatementToElkDO setModifyColumn(String modifyColumn) {
        this.modifyColumn = modifyColumn;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public SqlStatementToElkDO setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public Integer getStatus() {
        return status;
    }

    public SqlStatementToElkDO setStatus(Integer status) {
        this.status = status;
        return this;
    }
}
