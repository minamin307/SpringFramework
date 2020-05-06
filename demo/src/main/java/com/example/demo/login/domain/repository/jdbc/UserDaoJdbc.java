package com.example.demo.login.domain.repository.jdbc;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.login.domain.model.User;
import com.example.demo.login.domain.repository.UserDao;

@Repository("UserDaoJdbcImpl")
public class UserDaoJdbc implements UserDao {

    @Autowired
    private JdbcTemplate jdbc;

    /**
     * ユーザー件数の取得
     */
    public int count() throws DataAccessException {

        int count = jdbc.queryForObject(
                "SELECT COUNT(*) FROM m_user", Integer.class);

        return count;
    }

    /**
     * ユーザーを取得
     */
    public User selectOne(String userId) throws DataAccessException {

        Map<String, Object> map = jdbc.queryForMap(
                "SELECT * FROM m_user WHERE user_id = ?", userId);

        return setUser(map);
    }

    /**
     * ユーザーを複数件取得
     */
    public List<User> selectMany() throws DataAccessException {

        List<Map<String, Object>> list = jdbc.queryForList("SELECT * FROM m_user");
        List<User> result = new ArrayList<>();
        for(Map<String, Object> map : list) {
            result.add(setUser(map));
        }

        return result;
    }

    /**
     * ユーザーの追加
     */
    public int insertOne(User user) throws DataAccessException {

        int count = jdbc.update("INSERT INTO m_user("
                + "user_id, password, user_name, birthday, age, marriage, role) "
                + "VALUES(?,?,?,?,?,?,?)",
                user.getUserId(),
                user.getPassword(),
                user.getUserName(),
                user.getBirthday(),
                user.getAge(),
                user.isMarriage(),
                user.getRole());

        return count;
    }

    /**
     * ユーザ更新
     */
    public int updateOne(User user) throws DataAccessException {

        int count = jdbc.update(
                "UPDATE m_user SET "
                + "password = ?,"
                + "user_name = ?,"
                + "birthday = ?,"
                + "age = ?,"
                + "marriage = ?"
                + "WHERE user_id = ?",
                user.getPassword(),
                user.getUserName(),
                user.getBirthday(),
                user.getAge(),
                user.isMarriage(),
                user.getUserId());

        return count;
    }

    /**
     * ユーザ削除
     */
    public int deleteOne(String userId) throws DataAccessException {

        int count = jdbc.update(
                "DELETE FROM m_user WHERE user_id = ?",
                userId);

        return count;
    }

    /**
     * CSV出力
     */
    public void userCsvOne() throws DataAccessException {

        String sql = "SELECT * FROM m_user";

        UserRowCallbackHandler handler = new UserRowCallbackHandler();

        jdbc.query(sql, handler);
    }

    private User setUser(Map<String, Object> map) {

        User user = new User();
        user.setUserId((String)map.get("user_id"));
        user.setPassword((String)map.get("password"));
        user.setUserName((String)map.get("user_name"));
        user.setBirthday((Date)map.get("birthday"));
        user.setAge((Integer)map.get("age"));
        user.setMarriage((Boolean)map.get("marriage"));
        user.setRole((String)map.get("role"));

        return user;
    }
}
