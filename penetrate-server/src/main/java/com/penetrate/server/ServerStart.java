package com.penetrate.server;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServerStart {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServerStart.class);
    public static void main(String[] args) throws Exception {
        LOGGER.info("哈哈");
        if (null != args && args.length == 2) {
            int visitorPort = Integer.parseInt(args[1]);
            int serverPort = Integer.parseInt(args[0]);
            Constant.visitorPort = visitorPort;
            Constant.serverPort = serverPort;
            // 启动访客服务端，用于接收访客请求
            VisitorSocket.startServer();
            // 启动代理服务端，用于接收客户端请求
            ServerSocket.startServer();
        }
    }
}
