package com.woc.am.service;

import com.woc.am.dao.LoginDao;
import com.woc.am.dto.LoginCredentialsDTO;
import com.woc.am.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class LoginService {

    @Autowired
    //@Qualifier("loginV1")
    @Qualifier("loginV2")
    private LoginDao loginDao;

    public boolean isValidUser(LoginCredentialsDTO loginCredentials){
        return loginDao.isValidUser(loginCredentials);
    }

    public UserDTO doLogin(LoginCredentialsDTO loginCredentials) {
        return loginDao.doLogin(loginCredentials);
    }

    public void resetPassword(String loginId, String oldPassword, String newPassword){
        loginDao.resetPassword(loginId, oldPassword, newPassword);
    }

    public Set<Integer> getAllOperation(String loginId) {

        //do the hashing after retrieval of data from db
        return Set.of(1,5);
    }

}
