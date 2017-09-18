package com.wasd11203.controller;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.springframework.stereotype.Component;

import com.wasd11203.vo.UserVo;

/**
 * @author AA
 * @date 2017-09-15 14:53
 */
@ServerEndpoint(value = "/endpoint") // socket 连接路径
@Component
public class WebSocketController {

    //静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
    private static int onlineCount = 0;

    //concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
    private static CopyOnWriteArraySet<WebSocketController> webSocketSet = new CopyOnWriteArraySet<WebSocketController>();

    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;

    private UserVo user;


    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session) {

        UserVo u = new UserVo();
        u.setId(UUID.randomUUID().toString());
        u.setName(UUID.randomUUID().toString());

        this.session = session;
        this.user = u;
        webSocketSet.add(this);     //加入set中
        addOnlineCount();           //在线数加1
        System.out.println("有新连接加入！当前总人数为" + getOnlineCount());

        try {
            broadcastSendMessage("有新连接加入！当前在线人数为" + getOnlineCount() + ",欢迎新人：" + this.user.getName() + "!!!", null);
        } catch (IOException e) {
            System.out.println("IO异常");
        }
    }

    /**
     * 收到客户端消息后调用的方法
     * 仅支持接收 string,binary,基本类型 数据参数
     *
     * @param message 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String message, Session session) throws IOException {
        System.out.println("来自客户端的消息:" + message);
        //群发消息
        broadcastSendMessage(this.user.getName() + "发送消息:" + message, this.session);
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() throws IOException {
        webSocketSet.remove(this);  //从set中删除
        subOnlineCount();           //在线数减1
        System.out.println("有一连接关闭！当前在线人数为" + getOnlineCount());
        broadcastSendMessage("有一连接关闭！当前在线人数为" + getOnlineCount() + ",下线人：" + this.user.getName(), this.session);
    }


    /**
     * 发生错误时调用
     */
    @OnError
    public void onError(Session session, Throwable error) {
        System.out.println("发生错误");
        error.printStackTrace();
    }

    /**
     * 给当前连接发送消息
     *
     * @param message
     * @throws IOException
     */
    public void sendMessage(String message) throws IOException {
        // 给自己发送消息
        this.session.getBasicRemote().sendText(message);
        //this.session.getAsyncRemote().sendText(message);
    }

    /**
     * 群发自定义消息
     */
    public static void broadcastSendMessage(String message, Session sourceSession) throws IOException {
        for (WebSocketController item : webSocketSet) {
            try {
                if (sourceSession != null && (item.session.getId() == sourceSession.getId())) {
                    item.sendMessage(message + ",SELF");
                }else{
                    item.sendMessage(message + ",OTHERS");
                }

            } catch (IOException e) {
                continue;
            }
        }
    }

    /**
     * 点对点发送消息
     *
     * @param sourceSessionId
     * @param message
     * @param targetSessionId
     * @throws IOException
     */
    public static void p2pSendMessage(String sourceSessionId, String message, String targetSessionId) throws IOException {
        for (WebSocketController item : webSocketSet) {
            try {
                if (item.session.getId() == targetSessionId) {
                    item.sendMessage(message);
                }
            } catch (IOException e) {
                continue;
            }
        }
    }


    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
        WebSocketController.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        WebSocketController.onlineCount--;
    }
}
