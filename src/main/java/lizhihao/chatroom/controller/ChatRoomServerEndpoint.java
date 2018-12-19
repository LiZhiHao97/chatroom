package lizhihao.chatroom.controller;

import com.alibaba.fastjson.JSONObject;
import lizhihao.chatroom.model.User;
import lizhihao.chatroom.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

import static lizhihao.chatroom.utils.WebSocketUtils.ONLINE_USER_SESSIONS;
import static lizhihao.chatroom.utils.WebSocketUtils.sendMessageAll;

@RestController
@ServerEndpoint("/chat-room/{username}")
public class ChatRoomServerEndpoint {
    private static final Logger logger = LoggerFactory.getLogger(ChatRoomServerEndpoint.class);

    private static UserRepository userRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        ChatRoomServerEndpoint.userRepository = userRepository;
    }

    @OnOpen
    public void openSession(@PathParam("username") String username, Session session) {
        JSONObject result = new JSONObject();
        System.out.println(username);
        User user = userRepository.findByUserName(username);
        ONLINE_USER_SESSIONS.put(username, session);
        String message = "欢迎用户[" + username + "] 来到聊天室！" ;
        result.put("msg", message);
        result.put("onlineCount", ONLINE_USER_SESSIONS.size());
        result.put("avatar", user.getAvatar());
        result.put("nickname", user.getNickname());
        logger.info("用户登录："+message);
        sendMessageAll(result.toJSONString());
    }

    @OnMessage
    public void onMessage(@PathParam("username") String username, String message) {
        JSONObject result = new JSONObject();
        User user = userRepository.findByUserName(username);
        result.put("msg", message);
        result.put("onlineCount", ONLINE_USER_SESSIONS.size());
        result.put("username", username);
        result.put("nickname", user.getNickname());
        result.put("avatar", user.getAvatar());
        logger.info("发送消息："+message);
        sendMessageAll(result.toJSONString());
    }

    @OnClose
    public void onClose(@PathParam("username") String username, Session session) {
        //当前的Session 移除
        ONLINE_USER_SESSIONS.remove(username);
        //并且通知其他人当前用户已经离开聊天室了
        JSONObject result = new JSONObject();
        String message = "用户[" + username + "] 已经离开聊天室了！";
        result.put("msg", message);
        result.put("onlineCount", ONLINE_USER_SESSIONS.size());;
        sendMessageAll(result.toJSONString());
        try {
            session.close();
        } catch (IOException e) {
            logger.error("onClose error",e);
        }
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        try {
            session.close();
        } catch (IOException e) {
            logger.error("onError excepiton",e);
        }
        logger.info("Throwable msg "+throwable.getMessage());
    }


}