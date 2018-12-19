package lizhihao.chatroom.resository;

import lizhihao.chatroom.ChatroomApplication;
import lizhihao.chatroom.model.Message;
import lizhihao.chatroom.repository.MessageRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ChatroomApplication.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class MessageRepositoryTests {
    @Autowired
    private MessageRepository messageRepository;

    @Test
    public void testSave() {
        Message message = new Message("lizhihao1", "lizhihao", "你好啊", "2018-12-17");
        messageRepository.save(message);
    }
}
