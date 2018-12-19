package lizhihao.chatroom.repository.impl;

import lizhihao.chatroom.model.User;
import lizhihao.chatroom.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepositoryImpl implements UserRepository {
   @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public int save(User user) {
        return jdbcTemplate.update("INSERT INTO user(username, password, nickname, avatar) values(?, ?, ?, ?)",
                user.getUsername(), user.getPassword(), user.getNickname(), user.getAvatar());
    }

   @Override
    public int update(User user) {
       return jdbcTemplate.update("UPDATE user SET password = ? , nickname = ? WHERE id=?",
               user.getPassword(), user.getNickname(), user.getId());
    }

    @Override
    public User findByUserName(String username) {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM user WHERE username=?", new Object[] { username }, new BeanPropertyRowMapper<User>(User.class));
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
}
