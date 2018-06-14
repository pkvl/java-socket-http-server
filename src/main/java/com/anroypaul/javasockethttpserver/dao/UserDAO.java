package com.anroypaul.javasockethttpserver.dao;

import com.anroypaul.javasockethttpserver.domain.User;

import java.util.List;

public interface UserDAO {

    List<User> findAll();

    int count();

    void save(User user);

    void update(User user);

    User getById(int id);

    void delete(int id);

    boolean isExist(int id);
}
