package lizhihao.chatroom.resository;

import lizhihao.chatroom.model.User;
import lizhihao.chatroom.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Repository;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserRepositoryTests {
    @Autowired
    private UserRepository userRepository;

    @Test
    public void testSave() {
        User user = new User("lizhihao1", "123", "lizhihao1");
        userRepository.save(user);
    }

    @Test
    public void testUpdate() {
        User user = userRepository.findById(3);
        user.setPassword("123456");
        userRepository.update(user);
    }

    @Test
    public void testFindById() {
        User user=userRepository.findById(3);
        System.out.println("user == "+user.getNickname());
    }
}
