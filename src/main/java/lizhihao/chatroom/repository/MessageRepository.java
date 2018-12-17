package lizhihao.chatroom.repository;

import lizhihao.chatroom.model.Message;

public interface MessageRepository {
    int save(Message message);
}
