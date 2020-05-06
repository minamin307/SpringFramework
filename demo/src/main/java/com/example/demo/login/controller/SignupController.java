package com.example.demo.login.controller;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.login.domain.model.GroupOrder;
import com.example.demo.login.domain.model.SignupForm;
import com.example.demo.login.domain.model.User;
import com.example.demo.login.domain.service.UserService;

@Controller
public class SignupController {

    @Autowired
    UserService service;

    @GetMapping("/signup")
    public String getSignup(@ModelAttribute SignupForm form, Model model) {

        Map<String, String> map = initRadioMarriage();

        model.addAttribute("radioMarriage", map);

        return "login/signup";
    }

    @PostMapping("/signup")
    public String postSignup(
            @ModelAttribute @Validated(GroupOrder.class) SignupForm form,
            BindingResult result ,Model model) {

        System.out.println(form);

        if (result.hasErrors()) {
            return getSignup(form, model);
        }

        // データを追加
        service.insertOne(userBySignupForm(form));

        return "redirect:/login";
    }

    private User userBySignupForm(SignupForm form) {

        User user = new User();
        user.setUserId(form.getUserId());
        user.setPassword(form.getPassword());
        user.setUserName(form.getUserName());
        user.setAge(form.getAge());
        user.setBirthday(form.getBirthday());
        user.setMarriage(form.isMarriage());
        user.setRole("ROLE");

        return user;
    }

    private Map<String, String> initRadioMarriage() {

        Map<String, String> map = new LinkedHashMap<String, String>();

        map.put("既婚", "true");
        map.put("未婚", "false");

        return map;
    }
}
