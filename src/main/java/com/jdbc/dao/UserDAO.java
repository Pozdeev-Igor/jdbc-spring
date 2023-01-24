package com.jdbc.dao;

import com.jdbc.model.User;

import java.util.List;

public interface UserDAO {
    //Create
    void save(User user);
    //Get All
    List<User> getAll();
}
