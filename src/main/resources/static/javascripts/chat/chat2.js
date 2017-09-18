/**
 * Created by AA on 2017/9/5
 * 群聊
 *
 */
'use strict';

var websocket = null;

//判断当前浏览器是否支持WebSocket
if ('WebSocket' in window) {
    websocket = new WebSocket("ws://localhost:8080/back/endpoint");
} else {
    alert('Not support websocket');
}

// 发送消息
function sendMessage() {
    var message = $("#message").val();
    websocket.send(message);
}

// 退出聊天
function closeWebSocket() {
    websocket.close();
}

//连接成功建立的回调方法
websocket.onopen = function (event) {
    console.log("建立连接");
}

//接收到消息的回调方法
websocket.onmessage = function (event) {
    setMessageInnerHTML(event.data);
}

//连接关闭的回调方法
websocket.onclose = function () {
    console.log("断开连接");
}

//连接发生错误的回调方法
websocket.onerror = function () {
    console.log("网络异常");
};

//监听窗口关闭事件，当窗口关闭时，主动去关闭websocket连接，防止连接还没断开就关闭窗口，server端会抛异常。
window.onbeforeunload = function () {
    websocket.close();
}

//将消息显示在网页上
function setMessageInnerHTML(message) {
    if(message.indexOf("SELF")!= -1){
        console.log("自身发送消息",message);
    }else{
        console.log("其他发送消息",message);
    }
}


