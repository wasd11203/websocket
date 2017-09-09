package com.wasd11203.controller;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 入口Controller
 * Created by AA on 2017/9/3.
 */
@Controller
public class Entry {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping("/")
    public String login() {
        return "views/login.html";
    }

    /**
     * 群聊入口
     * @param session
     * @return
     */
    @RequestMapping("/login1")
    public String toChat(HttpSession session) {
        int uId = 1;
        String username = "test1";
        session.setAttribute("username", username);
        session.setAttribute("uId", uId);

        logger.info("入口参数 -- username:[{}],uId:[{}]", username, uId);

        return "views/chat1.html";
    }

    /**
     * 私聊入口
     */
    /**
     * 私聊： 用户1
     * @param session
     * @return
     */
    @RequestMapping("/login2")
    public String toPrivateChat1(HttpSession session) {
        int uId = 2;
        String username = "test2";
        session.setAttribute("username", username);
        session.setAttribute("uId", uId);

        logger.info("入口参数 -- username:[{}],uId:[{}]", username, uId);

        return "views/chat2.html";
    }

    /**
     * 私聊： 用户2
     * @param session
     * @return
     */
    @RequestMapping("/login3")
    public String toPrivateChat2(HttpSession session) {
        int uId = 3;
        String username = "test3";
        session.setAttribute("username", username);
        session.setAttribute("uId", uId);

        logger.info("入口参数 -- username:[{}],uId:[{}]", username, uId);

        return "views/chat3.html";
    }

}
