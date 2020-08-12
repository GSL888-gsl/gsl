package com.isoft.service;

import com.isoft.entity.Users;

import java.util.Map;

public interface UserService {
    Users getById(Integer id);


    boolean register(Users users);

    Map<String, Object> getLogin(String name, String password);

    boolean updatePhoto(Users users);

    int updatePassword(Integer id,String oldpass,String newpass);

    boolean getNameCount(String name);



}
