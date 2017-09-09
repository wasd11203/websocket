package com.wasd11203.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

/**
 * Created by AA on 2017/9/4.
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig extends AbstractWebSocketMessageBrokerConfigurer {
    @Override
    public void registerStompEndpoints(StompEndpointRegistry stompEndpointRegistry) {
        // //允许使用socketJs方式访问，访问点为endpoint，允许跨域
        stompEndpointRegistry.addEndpoint("/endpoint").setAllowedOrigins("*").withSockJS();

    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {

        //订阅Broker名称
        registry.enableSimpleBroker("/group","/private");
        //全局使用的订阅前缀（客户端订阅路径上会体现出来）
//        registry.setApplicationDestinationPrefixes("/g/"); // 服务端接收需要广播的消息路径前缀
        //点对点使用的订阅前缀（客户端订阅路径上会体现出来），不设置的话，默认是/user/
        //registry.setUserDestinationPrefix("/p/");// 服务端接收需要点对点发送的消息路径前缀

    }
}