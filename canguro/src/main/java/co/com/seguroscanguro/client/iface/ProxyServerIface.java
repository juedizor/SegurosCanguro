/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.seguroscanguro.client.iface;

import io.netty.buffer.ByteBuf;
import io.reactivex.netty.protocol.http.server.HttpServer;

/**
 *
 * @author jeio
 */
public interface ProxyServerIface {
    
    public HttpServer<ByteBuf, ByteBuf> createServer();
    
}
