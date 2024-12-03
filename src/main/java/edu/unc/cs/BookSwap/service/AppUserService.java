package edu.unc.cs.BookSwap.service;

import edu.unc.cs.BookSwap.dto.UserDto;
import edu.unc.cs.BookSwap.entity.User;

import java.util.List;

public interface AppUserService {
    void saveUser(UserDto userDto);

    User findUserByEmail(String email);

    List<UserDto> findAllUsers();
}
