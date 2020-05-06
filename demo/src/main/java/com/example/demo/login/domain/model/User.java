package com.example.demo.login.domain.model;

import java.util.Date;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class User {

    @NotNull
    private String userId;

    @NotNull
    private String password;

    @NotNull
    private String userName;

    @DateTimeFormat(pattern="yyyy/MM/dd")
    private Date birthday;

    @Max(100)
    @Min(20)
    private int age;

    private boolean marriage;

    private String role;
}
