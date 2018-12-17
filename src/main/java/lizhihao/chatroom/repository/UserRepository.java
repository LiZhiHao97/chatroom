package lizhihao.chatroom.repository;

import lizhihao.chatroom.model.User;

public interface UserRepository  {
    int save(User user);
    int update(User user);
    User findByUserName(String username);
}
