package com.embl.person.service;

import com.embl.person.entity.User;

public interface UserService {

    User getUserByUsername(String username);
}
