package com.woc.am.dao.impl;

import com.woc.am.AMUtil;
import com.woc.am.dao.LoginDao;
import com.woc.am.dao.impl.assembler.UserAssembler;
import com.woc.am.dto.LoginCredentialsDTO;
import com.woc.am.dto.UserDTO;
import com.woc.am.persistence.hibernate.model.User;
import com.woc.am.persistence.jpa.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service("loginV2")
public class HibernateLoginDaoImpl implements LoginDao {
    private static final Logger logger = LoggerFactory.getLogger(HibernateLoginDaoImpl.class);

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserAssembler userAssembler;

    @Override
    public boolean isValidUser(LoginCredentialsDTO loginCredentials) {
        logger.debug("requested input={}", loginCredentials);
        String loginId = AMUtil.hash(loginCredentials.getLoginId());
        String password = AMUtil.hash(loginCredentials.getPassword());
        User user = userRepository.findByLoginIdAndPassword(loginId, password);
        if(null == user){
            return false;
        }

        return true;
    }

    @Override
    public UserDTO doLogin(LoginCredentialsDTO loginCredentials) {
        logger.debug("requested input={}", loginCredentials);
        String loginId = AMUtil.hash(loginCredentials.getLoginId());
        String password = AMUtil.hash(loginCredentials.getPassword());
        User user = userRepository.findByLoginIdAndPassword(loginId, password);
        return userAssembler.fromDomain(user);
    }

    @Transactional
    @Override
    public void resetPassword(String loginId, String oldPassword, String newPassword) {
        logger.debug("requested input loginId={}, oldPassword={}, newPassword={}", loginId, oldPassword, newPassword);
        String hashedLoginId = AMUtil.hash(loginId);
        String hashedOldPassword = AMUtil.hash(oldPassword);

        User user = userRepository.findByLoginIdAndPassword(hashedLoginId, hashedOldPassword);

        if(null == user){
            throw new IllegalArgumentException("Invalid loginId or password for given loginId=" + loginId);
        }

        user.setPassword(AMUtil.hash(newPassword));
        userRepository.save(user);
    }

}
