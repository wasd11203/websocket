package com.wasd11203.vo;

import java.io.Serializable;

/**
 * 广播消息
 *
 * @author AA
 * @date 2017-09-04 22:02
 */
public class BrodcastMessage implements Serializable{

    private String uId;

    private String username;

    private String message;

    public BrodcastMessage() {
    }

    public BrodcastMessage(String uId, String username, String message) {
        this.uId = uId;
        this.username = username;
        this.message = message;
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}