package lizhihao.chatroom.repository.impl;

import lizhihao.chatroom.model.Message;
import lizhihao.chatroom.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class MessageRepositoryImpl implements MessageRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public int save(Message message) {
        return jdbcTemplate.update("INSERT INTO message(from_user, to_user, content, `time`) values(?, ?, ?, ?)",
                message.getFromUser(), message.getToUser(), message.getContent(), message.getTime());
    }
}
