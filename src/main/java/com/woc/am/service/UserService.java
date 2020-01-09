package com.woc.am.service;

import com.woc.am.dao.UserDao;
import com.woc.am.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserDao userDao;

    public UserDTO createUser(UserDTO user){
        return userDao.createUser(user);
    }

    public UserDTO findByLoginId(String loginId) {
        return userDao.findByLoginId(loginId);
    }

    public UserDTO findById(Integer id) {
        return userDao.findById(id);
    }

    public void deleteUser(String loginId){
        userDao.deleteUser(loginId);
    }

    public List<UserDTO> searchUser(String userName) {
        return userDao.searchUser(userName);
    }

}
