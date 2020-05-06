package com.example.demo.login.domain.service;

import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.example.demo.login.domain.model.User;
import com.example.demo.login.domain.repository.UserDao;

@Service
public class UserService {

    @Autowired
    @Qualifier("UserDaoJdbcImpl3")
    UserDao dao;

    public int count() {
        return dao.count();
    }

    public User selectOne(String userId) {
        return dao.selectOne(userId);
    }

    public List<User> selectMany() {
        return dao.selectMany();
    }

    public boolean insertOne(User user) {

        int count = dao.insertOne(user);

        boolean result = false;
        if(count < 0) {
            result = true;
        }

        return result;
    }

    public boolean updateOne(User user) {

        int count = dao.updateOne(user);

        boolean result = false;
        if(count < 0) {
            result = true;
        }

        return result;
    }

    public boolean deleteOne(String userId) {

        int count = dao.deleteOne(userId);

        boolean result = false;
        if(count < 0) {
            result = true;
        }

        return result;
    }

    public void userCsvOne() {
        dao.userCsvOne();
    }

    public byte[] getFile(String fileName) throws IOException {

        FileSystem fs = FileSystems.getDefault();

        Path p = fs.getPath(fileName);

        byte[] bytes = Files.readAllBytes(p);

        return bytes;
    }
}
