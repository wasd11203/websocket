package com.wasd11203.controller;

import javax.websocket.Session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import com.wasd11203.vo.BrodcastMessage;
import com.wasd11203.vo.P2PMessage;

/**
 * Created by AA on 2017/9/3.
 */
@Controller
public class WebSocketController {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 群聊
     * @param message
     * @return
     */
    @MessageMapping("/groupchat")
    //SendTo 发送至 Broker 下的指定订阅路径
    @SendTo("/group/chatcontent")
    public BrodcastMessage groupChat(BrodcastMessage message) {
        //方法用于广播测试
        logger.info(message.getUsername());

        BrodcastMessage res = new BrodcastMessage();
        res.setMessage(message.getMessage());
        return res;
    }

    /**
     * 私聊
     */
    //注入SimpMessagingTemplate 用于点对点消息发送
    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/privatechat")
    // 发送的订阅路径为/user/{userId}/message
    // /user/路径是默认的一个，如果想要改变，必须在config 中setUserDestinationPrefix
    public void cheatTo(P2PMessage toUserMessage){
        //方法用于点对点测试
        System.out.println("Message= " + toUserMessage.getMessage());
        System.out.println("Username = " + toUserMessage.getUsername());
        messagingTemplate.convertAndSendToUser(toUserMessage.getuId(),"/message",toUserMessage.getMessage());
    }

}
