package com.woc.am.dao.impl;

import com.woc.am.AMUtil;
import com.woc.am.dao.LoginDao;
import com.woc.am.dto.LoginCredentialsDTO;
import com.woc.am.dto.UserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;

@Service("loginV1")
public class JdbcLoginDaoImpl implements LoginDao {
    private static final Logger logger = LoggerFactory.getLogger(JdbcLoginDaoImpl.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public boolean isValidUser(LoginCredentialsDTO loginCredentials) {
        logger.debug("requested input={}", loginCredentials);
        String loginId = AMUtil.hash(loginCredentials.getLoginId());
        String password = AMUtil.hash(loginCredentials.getPassword());
        String sql = "select count(*)=1 from am_user where login_id='" + loginId + "' and password='" + password +"'";
        logger.debug("query={}", sql);
        Boolean isVaidUser = jdbcTemplate.queryForObject(sql, Boolean.class);
        return isVaidUser;
    }

    public UserDTO doLogin(LoginCredentialsDTO loginCredentials) {
        logger.debug("requested input={}", loginCredentials);
        String loginId = AMUtil.hash(loginCredentials.getLoginId());
        String password = AMUtil.hash(loginCredentials.getPassword());
        String sql = "select id, user_name from am_user where login_id='" + loginId + "' and password='" + password +"'";
        logger.debug("query={}", sql);

        try{
            UserDTO userDTO = jdbcTemplate.queryForObject(sql, new RowMapper<UserDTO>(){
                @Override
                public UserDTO mapRow(ResultSet resultSet, int i) throws SQLException {
                    UserDTO userDTO = new UserDTO();
                    userDTO.setId(resultSet.getInt("id"));
                    userDTO.setLoginId(loginCredentials.getLoginId());
                    userDTO.setUserName(resultSet.getString("user_name"));
                    return userDTO;
                }
            });

            return userDTO;
        }catch (EmptyResultDataAccessException e){
            return null;
        }
    }

    @Override
    public void resetPassword(String loginId, String oldPassword, String newPassword) {
        logger.debug("requested input loginId={}, oldPassword={}, newPassword={}", loginId, oldPassword, newPassword);

        LoginCredentialsDTO loginCredentialsDTO = new LoginCredentialsDTO();
        loginCredentialsDTO.setLoginId(loginId);
        loginCredentialsDTO.setPassword(oldPassword);
        boolean validUser = isValidUser(loginCredentialsDTO);

        if(!validUser){
            throw new IllegalArgumentException("Invalid loginId or password for given loginId=" + loginId);
        }

        String hashedLoginId = AMUtil.hash(loginId);
        String hashedOldPassword = AMUtil.hash(oldPassword);

        String sql = "update am_user set password='"+AMUtil.hash(newPassword)+"' where login_id='" + hashedLoginId + "' and password='" + hashedOldPassword +"'";
        logger.debug("query={}", sql);

        jdbcTemplate.execute(sql);
    }
}
