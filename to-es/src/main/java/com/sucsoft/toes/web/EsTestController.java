package com.sucsoft.toes.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sucsoft.toes.auto.AutoElkService;
import com.sucsoft.toes.bean.*;
import com.sucsoft.toes.dao.SqlStatementMapper;
import com.sucsoft.toes.service.IDescriptionService;
import com.sucsoft.toes.service.IEsHelpService;
import com.sucsoft.toes.service.IStatementService;
import com.sucsoft.toes.service.impl.StatementServiceImpl;
import io.swagger.annotations.*;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.client.Request;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.*;

/**
 * <br>-lastModify:2019/8/21 8:34
 *
 * @author Lixiaoban
 * @version 1.0
 */
@RestController
@RequestMapping("es")
public class EsTestController {

    private IStatementService statementService;

    private IEsHelpService helpService;

    private IDescriptionService descriptionService;

    @Autowired
    public EsTestController(IStatementService statementService,IEsHelpService helpService,IDescriptionService descriptionService){
        this.statementService = statementService;
        this.helpService = helpService;
        this.descriptionService = descriptionService;
    }

    @ApiOperation("规则-API-01-查询所有的规则")
    @ApiOperationSupport(order = 1,author = "Libin")
    @GetMapping("rule")
    public List<SqlStatementToElkDO> findAll() {
        return statementService.findAll();
    }

    @ApiOperation("规则-API-02-查询一条规则")
    @ApiOperationSupport(order = 2,author = "Libin")
    @GetMapping("rule/one")
    @ApiImplicitParam(value = "id",name = "id",required = true,paramType = "query")
    public SqlStatementToElkDO findOne(@RequestParam String id) {
        return statementService.findOne(id);
    }

    @ApiOperation("规则-API-03-关闭一条规则")
    @ApiOperationSupport(order = 3,author = "Libin")
    @GetMapping("rule/close")
    @ApiImplicitParam(value = "id",name = "id",required = true,paramType = "query")
    public void close(@RequestParam String id) {
        statementService.close(id);
    }

    @ApiOperation("规则-API-04-开启一条规则")
    @ApiOperationSupport(order = 4,author = "Libin")
    @GetMapping("rule/open")
    @ApiImplicitParam(value = "id",name = "id",required = true,paramType = "query")
    public void open(@RequestParam String id) {
        statementService.open(id);
    }

    @PostMapping("rule/add")
    @ApiOperationSupport(order = 5,author = "Libin")
    @ApiOperation("规则-API-05-新建一条规则，默认开启状态")
    public void addOne(@RequestBody @ApiParam("实际内容") SqlStatementToElkDO elkDO) {
        statementService.addOne(elkDO);
    }

    @ApiOperation("规则-API-06-删除一条规则")
    @ApiOperationSupport(order = 5,author = "Libin")
    @ApiImplicitParam(name = "id",value = "id",required = true,paramType = "query")
    @DeleteMapping("rule")
    public void deleteOne(@RequestParam String id) {
        statementService.deleteOne(id);
    }

    /**
     * 根据indexName查询其所有的注释信息
     * @param indexName 索引名称
     * @return 所有注释信息
     */
    @GetMapping("desc")
    @ApiOperation("注释-API-01-根据索引名查询注释信息")
    @ApiOperationSupport(order = 6,author = "Libin")
    @ApiImplicitParam(value = "索引名",name = "indexName",required = true,paramType = "query",dataType = "string")
    public List<ColumnDescVO> findByIndexName(@RequestParam String indexName){
        return descriptionService.findByIndexName(indexName);
    }

    /**
     * 根据indexName添加或更新所有注释信息(此操作会重置之前存储的信息)
     * @param indexName 索引名称
     * @param data 注释信息
     */
    @PostMapping("desc/addAll")
    @ApiOperation(value = "注释-API-02-根据indexName添加或更新所有注释信息(谨慎使用)",notes = "此操作会重置之前存储的信息")
    @ApiOperationSupport(order = 7,author = "Libin")
    @ApiImplicitParam(value = "索引名",name = "indexName",required = true,paramType = "query",dataType = "string")
    public void addDescToIndexName(@RequestParam String indexName,@RequestBody @ApiParam("信息") List<SimpleColumnDescVO> data){
        descriptionService.addDescToIndexName(indexName, data);
    }

    /**
     * 根据indexName删除这个indexName下的所有注释信息
     * @param indexName 索引名称
     */
    @DeleteMapping("desc/deleteAll")
    @ApiOperation(value = "注释-API-03-根据indexName删除这个indexName下的所有注释信息")
    @ApiOperationSupport(order = 8,author = "Libin")
    @ApiImplicitParam(value = "索引名",name = "indexName",required = true,paramType = "query",dataType = "string")
    public void removeOneIndexDesc(@RequestParam String indexName){
        descriptionService.removeOneIndexDesc(indexName);
    }

    /**
     * 更新单个注释信息
     * @param indexName 索引名称
     * @param columnName 字段名称
     * @param updateDesc 更新的注释
     */
    @PutMapping("desc")
    @ApiOperation(value = "注释-API-04-更新单个注释信息")
    @ApiOperationSupport(order = 9,author = "Libin")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "索引名",name = "indexName",required = true,paramType = "query",dataType = "string"),
            @ApiImplicitParam(value = "字段名",name = "columnName",required = true,paramType = "query",dataType = "string"),
            @ApiImplicitParam(value = "更新内容",name = "updateDesc",required = true,paramType = "query",dataType = "string")
    })
    public void updateOneRecord(@RequestParam String indexName,@RequestParam String columnName,@RequestParam String updateDesc){
        descriptionService.updateOneRecord(indexName, columnName, updateDesc);
    }

    /**
     * 添加单个注释信息
     * @param indexName 索引名称
     * @param columnName 字段名称
     * @param addDesc 新增的注释
     */
    @PostMapping("desc")
    @ApiOperation(value = "注释-API-05-添加单个注释信息")
    @ApiOperationSupport(order = 10,author = "Libin")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "索引名",name = "indexName",required = true,paramType = "query",dataType = "string"),
            @ApiImplicitParam(value = "字段名",name = "columnName",required = true,paramType = "query",dataType = "string"),
            @ApiImplicitParam(value = "新增内容",name = "addDesc",required = true,paramType = "query",dataType = "string")
    })
    public void addOneRecord(@RequestParam String indexName,@RequestParam String columnName,@RequestParam String addDesc){
        descriptionService.addOneRecord(indexName, columnName, addDesc);
    }

    /**
     * 删除单个注释信息
     * @param indexName 索引名称
     * @param columnName 字段名称
     */
    @DeleteMapping("desc")
    @ApiOperation(value = "注释-API-06-删除单个注释信息")
    @ApiOperationSupport(order = 11,author = "Libin")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "索引名",name = "indexName",required = true,paramType = "query",dataType = "string"),
            @ApiImplicitParam(value = "字段名",name = "columnName",required = true,paramType = "query",dataType = "string")
    })
    public void removeOneRecord(@RequestParam String indexName,@RequestParam String columnName){
        descriptionService.removeOneRecord(indexName, columnName);
    }

    @GetMapping("search")
    @ApiOperation("查询-API-01-泛查询==跨index查询")
    @ApiOperationSupport(order = 12,author = "Libin")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "查询内容",name = "name",required = true,paramType = "query",dataType = "string"),
            @ApiImplicitParam(value = "页记录数",name = "pageSize",required = false,paramType = "query",dataType = "int",defaultValue = "1",example = "1"),
            @ApiImplicitParam(value = "页码",name = "pageNo",required = false,paramType = "query",dataType = "int",defaultValue = "10",example = "10")
    })
    public Page<Map> search(String name, Integer pageSize, Integer pageNo) throws Exception{
        return helpService.search(name, pageSize, pageNo);
    }

    @GetMapping("searchByIndexName")
    @ApiOperation(value = "查询-API-02-同index下泛查询",notes = "参与过滤的字段不允许为句子，尽可能使用短词进行过滤，否则结果会不真实")
    @ApiOperationSupport(order = 13,author = "Libin")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "索引名，支持*替代所有，类似index*,*,*index等",name = "indexName",required = true,paramType = "query",dataType = "string"),
            @ApiImplicitParam(value = "查询内容",name = "name",required = true,paramType = "query",dataType = "string"),
            @ApiImplicitParam(value = "页记录数",name = "pageSize",required = false,paramType = "query",dataType = "int",defaultValue = "1",example = "1"),
            @ApiImplicitParam(value = "页码",name = "pageNo",required = false,paramType = "query",dataType = "int",defaultValue = "10",example = "10"),
            @ApiImplicitParam(value = "字段名，数量与value一致",name = "key",required = false,paramType = "query",dataType = "string",allowMultiple = true),
            @ApiImplicitParam(value = "字段值，数量与key一致",name = "value",required = false,paramType = "query",dataType = "string",allowMultiple = true)
    })
    public Page<Map> search(String indexName, String name,Integer pageSize, Integer pageNo, String[] key, String[] value) throws IOException{
        return helpService.search(indexName, name, pageSize, pageNo, key, value);
    }

    @GetMapping("analyze")
    @ApiOperation(value = "查询-API-03-查询对应字分词结果")
    @ApiOperationSupport(order = 14,author = "Libin")
        @ApiImplicitParam(value = "查询名称" , name = "name", required = true, paramType = "query", dataType = "String", defaultValue = "你好么")
    public List<String> analyze(String name) throws IOException {
        return helpService.analyze(name);
    }
}
