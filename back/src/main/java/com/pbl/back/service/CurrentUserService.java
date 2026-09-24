package com.pbl.back.service;

import com.pbl.back.domain.entity.User;

// service/CurrentUserService.java
public interface CurrentUserService {
    User get();
    Long getId();
}
