package com.bealean.scrummy.dao;

import com.bealean.scrummy.model.User;

import java.util.List;

public interface UserDAO {

    List<User> findAll();

    User findByUsername(String username);

    int findIdByUsername(String username);

    boolean create(String username, String password, String role);

}
