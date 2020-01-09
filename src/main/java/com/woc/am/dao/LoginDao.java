package com.woc.am.dao;

import com.woc.am.dto.LoginCredentialsDTO;
import com.woc.am.dto.UserDTO;

public interface LoginDao {
    boolean isValidUser(LoginCredentialsDTO loginCredentials);
    UserDTO doLogin(LoginCredentialsDTO loginCredentials);
    void resetPassword(String loginId, String oldPassword, String newPassword);
}
