package com.sucsoft.toes.config;

import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.apache.http.impl.nio.reactor.IOReactorConfig;
import org.apache.http.message.BasicHeader;
import org.elasticsearch.client.Node;
import org.elasticsearch.client.NodeSelector;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.InputStream;
import java.nio.file.Files;
import java.security.KeyStore;
import java.util.Iterator;

/**
 * <br>-lastModify:2019/8/20 22:29
 *
 * @author Lixiaoban
 * @version 1.0
 */
@Configuration
public class ElasticConfiguration {

    @Value("${es.hostname}")
    private String esIp;
    @Bean
    public RestClient client(){
        //多个节点可以在内部多new几个HttpHost
        RestClientBuilder builder = RestClient.builder(new HttpHost(esIp,9200,"http"));
        //设置请求头
        Header[] headers = {new BasicHeader("header","value")};
        builder.setDefaultHeaders(headers);

        //设置超时时间,多次尝试的同一请求应该遵守的超时。默认值为30s,与默认认套接字超时相同。若自定义套接字超时，则应相应地调整最大重试超时
        builder.setMaxRetryTimeoutMillis(60000);

        //设置失败监听器，每次节点失败监听，可以额外处理
        builder.setFailureListener(new RestClient.FailureListener(){
            @Override
            public void onFailure(Node node) {
                super.onFailure(node);
                System.out.println(node.getName() + "====节点失败");
            }
        });

        //配置节点选择器。客户端以循环方式将每个请求发送到每一个配置的节点上
        //发送请求的节点，用于过滤客户端。将请求发送到这些客户端节点。默认向每个配置节点发送
        //这个配置通常是用户在启用嗅探时向专用主节点发送请求，即只有专用的主节点应该被HTTP请求命中
        builder.setNodeSelector(NodeSelector.SKIP_DEDICATED_MASTERS);
        //进行详细的配置
//        builder.setNodeSelector(new NodeSelector() {
//            @Override
//            public void select(Iterable<Node> nodes) {
//                boolean foundOne = false;
//                for (Node node: nodes) {
//                    String rackId = node.getAttributes().get("rack_id").get(0);
//                    if ("rack_one".equals(rackId)) {
//                        foundOne = true;
//                        break;
//                    }
//                }
//                if (foundOne) {
//                    Iterator<Node> nodesIt = nodes.iterator();
//                    while (nodesIt.hasNext()) {
//                        Node node = nodesIt.next();
//                        String rackId = node.getAttributes().get("rack_id").get(0);
//                        if (!"rack_one".equals(rackId)) {
//                            nodesIt.remove();
//                        }
//                    }
//                }
//            }
//        });



//        //如果ES设置了密码，那这里也提供了一个基本的认证机制，下面设置了ES需要基本身份验证的默认凭据提供程序
//        final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
//        credentialsProvider.setCredentials(AuthScope.ANY,
//                new UsernamePasswordCredentials("user", "password"));
//        builder.setHttpClientConfigCallback(new RestClientBuilder.HttpClientConfigCallback() {
//            @Override
//            public HttpAsyncClientBuilder customizeHttpClient(HttpAsyncClientBuilder httpClientBuilder) {
//                return httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
//            }
//        });
//
//        //上面采用异步机制实现抢先认证，这个功能也可以禁用，这意味着每个请求都将在没有授权标头的情况下发送，然后查看它是否被接受，
//        //并且在收到HTTP 401响应后，它再使用基本认证头重新发送完全相同的请求，这个可能是基于安全、性能的考虑
//        builder.setHttpClientConfigCallback(new RestClientBuilder.HttpClientConfigCallback() {
//            @Override
//            public HttpAsyncClientBuilder customizeHttpClient(HttpAsyncClientBuilder httpClientBuilder) {
//                // 禁用抢先认证的方式
//                httpClientBuilder.disableAuthCaching();
//                return httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
//            }
//        });

        //配置通信加密，有多种方式：setSSLContext、setSSLSessionStrategy和setConnectionManager
        //(它们的重要性逐渐递增)
//        KeyStore truststore = KeyStore.getInstance("jks");
//        try (InputStream is = Files.newInputStream(keyStorePath)) {
//            truststore.load(is, keyStorePass.toCharArray());
//        }
//        SSLContextBuilder sslBuilder = SSLContexts.custom().loadTrustMaterial(truststore, null);
//        final SSLContext sslContext = sslBuilder.build();

        //配置异步请求的线程数量Apache Http Async Client默认启动一个调度程序线程，以及由连接管理器使用的许多工作线程
        //（与本地检测到的处理器数量一样多，取决于Runtime.getRuntime().availableProcessors()返回的数量）。线程数可以修改如下,
        //这里是修改为1个线程，即默认情况
        builder.setHttpClientConfigCallback(new RestClientBuilder.HttpClientConfigCallback() {
            @Override
            public HttpAsyncClientBuilder customizeHttpClient(HttpAsyncClientBuilder httpAsyncClientBuilder) {
                return httpAsyncClientBuilder.setDefaultIOReactorConfig(IOReactorConfig
                        .custom().setIoThreadCount(1)
                        .build());
//                        .setSSLContext(sslContext);
            }
        });

        //配置连接超时和套接字超时
        //配置请求超时时，将连接超时(默认1s)和套接字超时(默认30s)增加
        //这里配置玩应该相应调整最大重试超时(默认30s),即上面的setMaxRetryTimeoutMillis，一般于最大的那个值一致即60000
        builder.setRequestConfigCallback(new RestClientBuilder.RequestConfigCallback() {
            @Override
            public RequestConfig.Builder customizeRequestConfig(RequestConfig.Builder builder) {
                return builder.setConnectTimeout(5000).setSocketTimeout(60000);
            }
        });

        //返回实体
        return builder.build();
    }
}
