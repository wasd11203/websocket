/**
 * Created by AA on 2017/9/5
 * 群聊
 *
 */
'use strict';

$(function(){
    connect();
});

// websocket 客户端
var stompClient = null;

/**
 * 连接websocket服务端
 */
function connect() {
    var socket = new SockJS('/back/endpoint'); // 连接websocket 的 server端
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        console.log('Connected:' + frame);
        stompClient.subscribe('/group/chatcontent', function (response) { // 关联接收消息路径('/'代表 websocket server端的绝对路径)
            showAcceptMessage(JSON.parse(response.body));
        })
    });
}

/**
 * 断开websocket连接
 */
function disconnect() {
    if (stompClient != null) {
        stompClient.disconnect();
    }
    console.log('Disconnected');
}

/**
 * 发送消息
 */
function sendMessage() {
    var uId = 1;
    var username = $("#username").val();
    var message = $("#message").val();
    // 从消息路径('/'代表 websocket server端的绝对路径)发送到server端
    stompClient.send("/groupchat", {}, JSON.stringify({'uId': uId, 'username': username, 'message': message}));
}

/**
 * 接收返回消息
 * @param message
 */
function showAcceptMessage(message) {
    console.log(message);
}