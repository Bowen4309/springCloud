package com.Bibo.gateway.utils;

import org.apache.commons.httpclient.ConnectTimeoutException;
import org.apache.commons.httpclient.params.HttpConnectionParams;
import org.apache.commons.httpclient.protocol.ProtocolSocketFactory;

import javax.net.SocketFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.net.*;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

public class HTTPSSecurePotocolocketFactory implements ProtocolSocketFactory {
    private SSLContext sslContext = null;

    private SSLContext createSSLContext(){
        SSLContext sslContext = null;
        try {
            sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null,new TrustManager[]{
                    new TrustAnyTrustManager()
            },new SecureRandom());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
        return sslContext;
    }


    public Socket createSocket(Socket socket,String host,int port,boolean autoClose) throws IOException {
        return getSslContext().getSocketFactory().createSocket(socket,host,port,autoClose);
    }

    private SSLContext getSslContext(){
        if (null == this.sslContext){
            this.sslContext = createSSLContext();
        }
        return this.sslContext;
    }

    @Override
    public Socket createSocket(String s, int i, InetAddress inetAddress, int i1) throws IOException, UnknownHostException {
       return getSslContext().getSocketFactory().createSocket(s,i,inetAddress,i1);
    }

    @Override
    public Socket createSocket(String s, int i, InetAddress inetAddress, int i1, HttpConnectionParams httpConnectionParams) throws IOException, UnknownHostException, ConnectTimeoutException {
        if (null == httpConnectionParams)
        {
            throw new IllegalArgumentException("httpConnectionParams may not be null");
        }
        int timeOut = httpConnectionParams.getConnectionTimeout();
        SocketFactory socketFactory = getSslContext().getSocketFactory();
        if(timeOut == 0){
            return socketFactory.createSocket(s,i,inetAddress,i1);
        }else{
            Socket socket = socketFactory.createSocket();
            SocketAddress localAddr = new InetSocketAddress(inetAddress,i1);
            SocketAddress remoteAddr = new InetSocketAddress(s,i);
            socket.bind(localAddr);
            socket.connect(remoteAddr,timeOut);
            return socket;
        }
    }

    @Override
    public Socket createSocket(String s, int i) throws IOException, UnknownHostException {
        return getSslContext().getSocketFactory().createSocket(s,i);
    }

    public static class TrustAnyTrustManager implements X509TrustManager{

        @Override
        public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

        }

        @Override
        public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[0];
        }
    }
}
