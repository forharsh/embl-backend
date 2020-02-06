package com.embl.backend.service;

import com.embl.backend.entity.User;

public interface UserService {

    User getUserByUsername(String username);
}
