/**
 * Project Name:TyspV4D_FS
 * File Name:test.java
 * Package Name:test
 * Date:2018年4月12日下午2:16:47
 * Copyright (c) 2018, All Rights Reserved.
 *
*/

package io.github.muxiaobai.java.Server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import jdk.nashorn.internal.ir.annotations.Ignore;

/**
 * ClassName:test <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2018年4月12日 下午2:16:47 <br/>
 * @author   Mu Xiaobai
 * @version  
 * @since    JDK 1.8
 * @see 	 
 */
public class HttpServer {
        // 用于判断是否需要关闭容器
        private boolean shutdown = false;
        
        public void acceptWait() {
          ServerSocket serverSocket = null;
          try {
              //端口号，最大链接数，ip地址
            serverSocket = new ServerSocket(8088, 1, InetAddress.getByName("127.0.0.1"));
          }
          catch (IOException e) {
              e.printStackTrace();
              System.exit(1); 
          }
          // 等待用户发请求
          while (!shutdown) {
            try {
              Socket socket = serverSocket.accept();
              InputStream is = socket.getInputStream();
              OutputStream  os = socket.getOutputStream();
              // 接受请求参数
              Request request = new Request(is);
              request.parse();
              // 创建用于返回浏览器的对象
              Response response = new Response(os);
              response.setRequest(request);
              response.sendStaticResource();
              //关闭一次请求的socket,因为http请求就是采用短连接的方式
              socket.close();
              //如果请求地址是/shutdown  则关闭容器
              if(null != request){
                   shutdown = request.getUrL().equals("/shutdown");
                   if(shutdown)serverSocket.close();
              }
            }
            catch (Exception e) {
                e.printStackTrace();
                continue;
            }
          }
        }
        public static void main(String[] args) {
              HttpServer server = new HttpServer();
              server.acceptWait();
        }
}

