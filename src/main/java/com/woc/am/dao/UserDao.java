package com.woc.am.dao;

import com.woc.am.dto.UserDTO;

import java.util.List;

public interface UserDao {
    UserDTO createUser(UserDTO user);
    UserDTO findByLoginId(String loginId);
    UserDTO findById(Integer id);
    void deleteUser(String loginId);

    List<UserDTO> searchUser(String userName);
}
