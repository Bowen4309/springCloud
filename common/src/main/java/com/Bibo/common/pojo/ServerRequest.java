package com.Bibo.common.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * 天河服务端的请求表单
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServerRequest<T> {

    private T params;

    private SeverPage page;

    private ServerConfig config;

    private Map<String,String> header;

    private String methods;

    public ServerRequest(T params,String start,String end,Long skip,Long limit){
        this.params = params;
        SeverPage severPage = new SeverPage(skip, limit);
        this.page = severPage;
//        LinkedList<String> timeRange = new LinkedList<>();
//        timeRange.add(start);
//        timeRange.add(end);
//        this.timeRange = timeRange;
        ServerConfig serverConfig = new ServerConfig();
        this.config = serverConfig;
    }

    public ServerRequest(T params,Long skip,Long limit){
        this.params = params;
        SeverPage severPage = new SeverPage(skip, limit);
        this.page = severPage;
        ServerConfig serverConfig = new ServerConfig();
        this.config = serverConfig;
    }

    public ServerRequest(Long skip,Long limit){
        this.params = params;
        SeverPage severPage = new SeverPage(skip, limit);
        this.page = severPage;
        ServerConfig serverConfig = new ServerConfig();
        this.config = serverConfig;
    }

    public ServerRequest(Map<String,String> header,String methods){
        this.header = header;
        this.methods = methods;
        ServerConfig serverConfig = new ServerConfig();
        this.config = serverConfig;
    }

    public ServerRequest(T params,String methods){
        this.params = params;
        this.methods=methods;
//        LinkedList<String> timeRange = new LinkedList<>();
//        timeRange.add(start);
//        timeRange.add(end);
//        this.timeRange = timeRange;
        ServerConfig serverConfig = new ServerConfig();
        this.config = serverConfig;
    }

}
