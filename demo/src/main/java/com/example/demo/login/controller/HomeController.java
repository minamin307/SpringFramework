package com.example.demo.login.controller;

import java.io.IOException;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.login.domain.model.SignupForm;
import com.example.demo.login.domain.model.User;
import com.example.demo.login.domain.service.UserService;

@Controller
public class HomeController {

    @Autowired
    UserService service;

    @GetMapping("/logout")
    public String postLogout() {
        // ログイン画面にリダイレクト
        return "redirect:/login";
    }

    @PostMapping("/home")
    public String postLogin(Model model) {

        model.addAttribute("contents", "login/home :: home_contents");

        return "login/homeLayout";
    }

    @GetMapping("/userList")
    public String getUserList(Model model) {

        // ユーザーリスト画面
        model.addAttribute("contents", "login/userList :: userList_contents");

        // ユーザー一覧取得
        List<User> userList = service.selectMany();

        // ユーザー数取得
        int count = service.count();

        model.addAttribute("userList", userList);
        model.addAttribute("userListCount", count);

        return "login/homeLayout";
    }

    @GetMapping("/userDetail/{id:.+}")
    public String getUserList(@ModelAttribute SignupForm form,
            Model model, @PathVariable("id") String userId) {

        model.addAttribute("contents", "login/userDetail :: userDetail_contents");

        Map<String, String> radioMarriage = initRadioMarriage();
        model.addAttribute("radioMarriage", radioMarriage);

        if (userId != null) {

            User user = service.selectOne(userId);
            form.setUserId(user.getUserId());
            form.setPassword(user.getPassword());
            form.setUserName(user.getUserName());
            form.setAge(user.getAge());
            form.setBirthday(user.getBirthday());
            form.setMarriage(user.isMarriage());

            System.out.println("signupForm:" + form);
            model.addAttribute("signupForm", form);
        }

        return "login/homeLayout";
    }

    @PostMapping(value="/userDetail", params="update")
    public String postUserDetailUpdate(@ModelAttribute SignupForm form, Model model) {

        // 更新処理
        User user = setUser(form);
        boolean result = service.updateOne(user);

        if(result) {
            model.addAttribute("result", "更新失敗");
        } else {
            model.addAttribute("result", "更新成功");
        }

        return getUserList(model);
    }

    @PostMapping(value="/userDetail", params="delete")
    public String postUserDetailDelete(@ModelAttribute SignupForm form, Model model) {

        // 削除処理
        String userId = form.getUserId();
        boolean result = service.deleteOne(userId);

        if(result) {
            model.addAttribute("result", "削除失敗");
        } else {
            model.addAttribute("result", "削除成功");
        }

        return getUserList(model);
    }

    @GetMapping("/csv")
    public String getCsv(Model model) {
        return getUserList(model);
    }

    @GetMapping("/userList/csv")
    public ResponseEntity<byte[]> getUserListCsv(Model model) {

        // ユーザーを全件取得して、CSVをサーバーに保存する
        service.userCsvOne();

        byte[] bytes = null;

        try {
            // サーバーに保存されているsample.csvファイルをbyteで取得する
            bytes = service.getFile("sample.csv");
        } catch(IOException e) {
            e.printStackTrace();
        }

        // HTTPヘッダーの設定
        HttpHeaders header = new HttpHeaders();
        header.add("Content-Type","text/csv; charset=UTF-8");
        header.setContentDispositionFormData("filename", "sample.csv");

        return new ResponseEntity<>(bytes, header, HttpStatus.OK);
    }

    private Map<String, String> initRadioMarriage() {

        Map<String, String> map = new LinkedHashMap<String, String>();

        map.put("既婚", "true");
        map.put("未婚", "false");

        return map;
    }

    private User setUser(Map<String, Object> map) {

        User user = new User();
        user.setUserId((String)map.get("user_id"));
        user.setPassword((String)map.get("password"));
        user.setUserName((String)map.get("user_name"));
        user.setBirthday((Date)map.get("birthday"));
        user.setAge((Integer)map.get("age"));
        user.setMarriage((Boolean)map.get("marriage"));

        return user;
    }

    private User setUser(SignupForm form) {

        User user = new User();
        user.setUserId(form.getUserId());
        user.setPassword(form.getPassword());
        user.setUserName(form.getUserName());
        user.setBirthday(form.getBirthday());
        user.setAge(form.getAge());
        user.setMarriage(form.isMarriage());

        return user;
    }
}
