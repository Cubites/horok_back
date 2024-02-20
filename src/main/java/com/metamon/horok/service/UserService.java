package com.metamon.horok.service;

import com.metamon.horok.dto.UserDTO;
import java.util.Optional;

public interface UserService {
    public Optional<UserDTO> getUserInfoByUserId (Integer userId);
}

