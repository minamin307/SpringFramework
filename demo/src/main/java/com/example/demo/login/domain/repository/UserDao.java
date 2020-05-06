package com.example.demo.login.domain.repository;

import java.time.DateTimeException;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.example.demo.login.domain.model.User;

@Repository
public interface UserDao {

    public int count() throws DateTimeException;
    public User selectOne(String userId) throws DateTimeException;
    public List<User> selectMany() throws DateTimeException;
    public int insertOne(User user) throws DateTimeException;
    public int updateOne(User user) throws DateTimeException;
    public int deleteOne(String userId) throws DateTimeException;
    public void userCsvOne() throws DateTimeException;
}
