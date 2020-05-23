package com.sucsoft.toes.service.impl;
import	java.util.stream.Collectors;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.sucsoft.toes.bean.ColumnDescVO;
import com.sucsoft.toes.bean.EsSearchDO;
import com.sucsoft.toes.bean.EsSearchResultVO;
import com.sucsoft.toes.bean.Page;
import com.sucsoft.toes.dao.SqlDescriptionMapper;
import com.sucsoft.toes.service.IEsHelpService;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.client.Request;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * <br>-lastModify:2019/8/21 18:15
 *
 * @author Lixiaoban
 * @version 1.0
 */
@Service
public class EsHelpServiceImpl implements IEsHelpService {

    private RestClient restClient;

    private ObjectMapper objectMapper;

    private SqlDescriptionMapper sqlDescriptionMapper;

    public EsHelpServiceImpl(RestClient restClient, ObjectMapper objectMapper, SqlDescriptionMapper sqlDescriptionMapper){
        this.restClient = restClient;
        this.objectMapper = objectMapper;
        this.sqlDescriptionMapper = sqlDescriptionMapper;
    }

    @Override
    public boolean createIndex(String indexName){
        Request request = new Request("PUT","/" + indexName.toLowerCase());
        Response response = null;
        try {
            response = restClient.performRequest(request);
            String s = EntityUtils.toString(response.getEntity());
            Map result = objectMapper.readValue(s, Map.class);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    @Override
    public boolean removeIndex(String indexName) throws IOException{
        Request request = new Request("DELETE","/" + indexName.toLowerCase());
        Response response = null;
        try {
            response = restClient.performRequest(request);
            String s = EntityUtils.toString(response.getEntity());
            Map result = objectMapper.readValue(s, Map.class);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    @Override
    public boolean updateIndexMapping(Map indexMapping, String indexName) throws IOException {
        Request request = new Request("POST","/" + indexName.toLowerCase() + "/_mapping");
        request.setJsonEntity(objectMapper.writeValueAsString(indexMapping));
        String s = EntityUtils.toString(restClient.performRequest(request).getEntity());
        Map result = objectMapper.readValue(s, Map.class);
        if(result.containsKey("error")){
            return false;
        }
        return true;
    }

    @Override
    public Map getIndexMapping(String indexName) throws IOException {
        Request request = new Request("GET","/" + indexName.toLowerCase() + "/_mapping");
        Response response = restClient.performRequest(request);
        String s = EntityUtils.toString(response.getEntity());
        Map map = objectMapper.readValue(s, Map.class);
        Map mappings = (Map)((Map) map.get(indexName.toLowerCase())).get("mappings");
        return mappings;
    }

    @Override
    public void saveItem(String json, String indexName, String idFieldName) throws IOException{
        Map map = objectMapper.readValue(json, Map.class);
        if(StringUtils.isEmpty(map.get(idFieldName))){
            throw new IllegalArgumentException("id为空");
        }
        Request request = new Request("POST","/" + indexName.toLowerCase() + "/_doc/" + map.get(idFieldName).toString());
        request.setJsonEntity(json);
        Response response = restClient.performRequest(request);
        String s = EntityUtils.toString(response.getEntity());
        //todo 失败处理机制
    }

    @Override
    public void saveItem(Map data, String indexName, String idFieldName) throws IOException,IllegalArgumentException{
        //如果取不到数据或者idFieldName为空的情况下，默认自动生成id
        String id = StringUtils.isEmpty(idFieldName)?"":Optional.ofNullable(data.get(idFieldName)).orElse("").toString();
        Request request = new Request("POST","/" + indexName.toLowerCase() + "/_doc/" + id);
        request.setJsonEntity(objectMapper.writeValueAsString(data));
        Response response = restClient.performRequest(request);
        String s = EntityUtils.toString(response.getEntity());
        //todo 失败处理机制
    }

    @Override
    public void saveItem(Object obj) throws IOException {

    }

    @Override
    public void saveItem(Object obj, String indexName) throws IOException {

    }

    @Override
    public Page<Map> search(String name, Integer pageSize, Integer pageNo) throws IOException {
        pageNo = Optional.ofNullable(pageNo).orElse(1);
        pageSize = Optional.ofNullable(pageSize).orElse(10);
        Request request = new Request("GET",
                String.format("*/_search?q=%s&from=%d&size=%d",name,(pageNo - 1) * pageSize,pageSize));
        Response response = restClient.performRequest(request);
        String s = EntityUtils.toString(response.getEntity());
        EsSearchDO searchDO = objectMapper.readValue(s, EsSearchDO.class);
        Map hits = searchDO.getHits();
        //获取数据总量 == todo 写死的内容日后是否可以完善
        Integer count = (Integer)((Map) hits.get("total")).get("value");
        List<Map> data = (List<Map>) hits.get("hits");
        //插入数据中提供的索引信息==>为减少数据库压力此处设置了缓存
        for (Map datum : data) {
            String indexName = datum.get("_index").toString();
            List<ColumnDescVO> byIndexName = sqlDescriptionMapper.findByIndexName(indexName);
            datum.put("_construct",byIndexName);
        }
        return new Page<>(data,count,pageNo,pageSize);
    }

    @Override
    public Page<Map> search(String indexName, String name,Integer pageSize, Integer pageNo, String[] key, String[] value) throws IOException {
        key = Optional.ofNullable(key).orElse(new String[0]);
        value = Optional.ofNullable(value).orElse(new String[0]);
        if(key.length != value.length){
            throw new IllegalArgumentException("key和value的数量应该相等");
        }
        pageNo = Optional.ofNullable(pageNo).orElse(1);
        pageSize = Optional.ofNullable(pageSize).orElse(10);
        Request request = new Request("POST", indexName + "/_search");
        //组装查询体
        Map<String, Object> params = Maps.newHashMapWithExpectedSize(16);
        params.put("from", (pageNo - 1) * pageSize);
        params.put("size",pageSize);
        Map<String, Map<String,List<Map>>> queryParams = Maps.newHashMapWithExpectedSize(16);
        Map<String,List<Map>> boolParams = Maps.newHashMapWithExpectedSize(16);
        List<Map> filterParams = Lists.newArrayList();
        //过滤内容录入==>>不提供算分
        for (int i = 0; i < key.length; i++) {
            filterParams.add(ImmutableMap.of("term",ImmutableMap.of(key[i],value[i])));
        }
        boolParams.put("filter",filterParams);
        //泛查询
        List<Map> mustParams = Lists.newArrayList();
        mustParams.add(ImmutableMap.of("query_string",ImmutableMap.of("query",name)));
        boolParams.put("must",mustParams);
        queryParams.put("bool",boolParams);
        params.put("query",queryParams);

//        //加亮查询
//        Map<String, Object> highLight = Maps.newHashMapWithExpectedSize(16);
//        highLight.put("pre_tags","<span class=\"" + className + "\">");
//        highLight.put("post_tags","</span>");
//        highLight.put("fields", ImmutableMap.of("contents",ImmutableMap.of()));
        request.setJsonEntity(objectMapper.writeValueAsString(params));
        Response response = restClient.performRequest(request);

        EsSearchDO searchDO = objectMapper.readValue(EntityUtils.toString(response.getEntity()), EsSearchDO.class);
        Map hits = searchDO.getHits();
        //获取数据总量 == todo 写死的内容日后是否可以完善
        Integer count = (Integer)((Map) hits.get("total")).get("value");
        List<Map> data = (List<Map>) hits.get("hits");
        //插入数据中提供的索引信息==>为减少数据库压力此处设置了缓存
        for (Map datum : data) {
            String indexNameTemp = datum.get("_index").toString();
            List<ColumnDescVO> byIndexName = sqlDescriptionMapper.findByIndexName(indexNameTemp);
            datum.put("_construct",byIndexName);
        }
        return new Page<>(data,count,pageNo,pageSize);
    }

    @Override
    public List<String> analyze(String name) throws IOException {
        Request request = new Request("POST",  "/_analyze");
        Map<String, Object> params = Maps.newHashMapWithExpectedSize(16);
        params.put("text",Lists.newArrayList(name));
        params.put("analyzer","ik_smart");
        request.setJsonEntity(objectMapper.writeValueAsString(params));
        Response response = restClient.performRequest(request);
        Map map = objectMapper.readValue(EntityUtils.toString(response.getEntity()), Map.class);
        //获取的结果中含tokens
        List<Map> tokens = (List<Map>) map.get("tokens");
        return tokens.stream().map(t -> t.get("token").toString()).collect(Collectors.toList());
    }
}
