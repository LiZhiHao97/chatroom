package lizhihao.chatroom.resository;

import lizhihao.chatroom.ChatroomApplication;
import lizhihao.chatroom.model.User;
import lizhihao.chatroom.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Repository;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ChatroomApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserRepositoryTests {
    @Autowired
    private UserRepository userRepository;

    @Test
    public void testSave() {
        User user = new User("lizhihao3", "123", "lizhihao2", 9);
        userRepository.save(user);
        System.out.println(1);
    }

    @Test
    public void testUpdate() {
        User user = userRepository.findByUserName("lizhihao");
        user.setPassword("123456");
        userRepository.update(user);
    }

    @Test
    public void testFindById() {
        User user=userRepository.findByUserName("lizhihao");
        System.out.println("user == "+user.getNickname());
    }
}
