/**
 * Created by AA on 2017/9/5
 * 私聊
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
    var socket = new SockJS('/back/endpoint');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        var uId = 1;
        console.log('Connected:' + frame);
        stompClient.subscribe('/private/'+uId+'/privatechat', function (response) {
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
    var uId = 2;
    var username = $("#username").val();
    var message = $("#message").val();
    stompClient.send("/private/"+uId+"/privatechat", {}, JSON.stringify({'uId': uId, 'username': username, 'message': message}));
}

/**
 * 接收返回消息
 * @param message
 */
function showAcceptMessage(message) {
    console.log(message);
}