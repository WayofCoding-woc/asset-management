package com.woc.am.dao.impl;

import com.woc.am.AMUtil;
import com.woc.am.dao.UserDao;
import com.woc.am.dao.impl.assembler.UserAssembler;
import com.woc.am.dto.UserDTO;
import com.woc.am.persistence.hibernate.model.User;
import com.woc.am.persistence.jpa.repository.UserRepository;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class HibernateUserDaoImpl implements UserDao {
    private static final Logger logger = LoggerFactory.getLogger(HibernateUserDaoImpl.class);

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserAssembler userAssembler;

    @Transactional
    @Override
    public UserDTO createUser(UserDTO userDTO) {
        User user = userAssembler.toDomain(userDTO);

        user.setLoginId(AMUtil.hash(user.getLoginId()));
        user.setPassword(AMUtil.hash(user.getPassword()));

        User savedUser = userRepository.save(user);
        return userAssembler.fromDomain(savedUser);
    }

    @Override
    public UserDTO findByLoginId(String loginId) {
        User user = userRepository.findByLoginId(AMUtil.hash(loginId));
        return userAssembler.fromDomain(user);
    }

    @Override
    public UserDTO findById(Integer id) {
        User user = userRepository.findById(id).get();
        return userAssembler.fromDomain(user);
    }

    @Transactional
    @Override
    public void deleteUser(String loginId) {
        String hashedLoginId = AMUtil.hash(loginId);
        User user = userRepository.findByLoginId(hashedLoginId);
        if(null == user){
            throw new IllegalArgumentException("User not found with loginId=" + loginId);
        }
        userRepository.delete(user);
    }

    @Override
    public List<UserDTO> searchUser(String userName) {
        List<User> users;
        if(StringUtils.isBlank(userName)){
            users = (List<User>)userRepository.findAll();
        }else {
            users = userRepository.findByUserNameContains(userName);
        }
        return userAssembler.fromDomain(users);
    }
}
