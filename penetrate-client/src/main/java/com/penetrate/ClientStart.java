package com.penetrate;

import com.penetrate.constant.Constant;
import com.penetrate.socket.ProxySocket;

public class ClientStart {
    public static void main(String[] args) throws Exception {
        if (null != args && args.length == 3) {
            int realPort = Integer.parseInt(args[2]);
            int serverPort = Integer.parseInt(args[1]);
            String serverIp = args[0];
            Constant.serverIp = serverIp;
            Constant.serverPort = serverPort;
            Constant.realPort = realPort;
            // 连接代理服务
            ProxySocket.connectProxyServer();
        }
    }
}