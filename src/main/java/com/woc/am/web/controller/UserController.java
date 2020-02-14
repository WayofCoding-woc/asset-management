package com.woc.am.web.controller;

import com.woc.am.AMUtil;
import com.woc.am.dto.UserDTO;
import com.woc.am.service.UserService;
import org.apache.commons.lang3.BooleanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @GetMapping(value = "/sessionExists")
    public ResponseEntity<?> sessionExists(HttpServletRequest request){
        HttpSession session = request.getSession(false);
        Map<String, Object> data = new HashMap<>();
        if(null != session){
            data.put("userName", session.getAttribute("userName"));
            data.put("session", session.getId());
            data.put("id", session.getAttribute("id"));
        }

        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    @PostMapping(value = "/registerUser")
    public ModelAndView registerUser(HttpServletRequest request){
        logger.debug("registerUser api got triggered");
        String loginId = request.getParameter("loginId");
        String password = request.getParameter("password");
        String user_name = request.getParameter("user_name");
        String email = request.getParameter("email");
        String mobile = request.getParameter("mobile");
        String doj = request.getParameter("doj");
        boolean active = BooleanUtils.toBoolean(request.getParameter("active"));

        UserDTO userDTO = new UserDTO();
        userDTO.setLoginId(loginId);
        userDTO.setPassword(password);
        userDTO.setUserName(user_name);
        userDTO.setEmail(email);
        userDTO.setMobile(Long.valueOf(mobile));
        userDTO.setDateOfJoining(AMUtil.parseDate(doj));
        userDTO.setActive(active);
        userDTO.setCreatedDate(new Date());

        logger.debug("registerUser api input={}", userDTO);

        UserDTO savedUser = null;
        try{
            savedUser = userService.createUser(userDTO);
        }catch (Exception e){
            logger.error("Unable to create user", e);
            return new ModelAndView("redirect:./../../user_registration_error.html");
        }

        return new ModelAndView("redirect:./../../view_user_details.html?id="+savedUser.getId());
    }

    @GetMapping(value = "/getUserDetailsById")
    public ResponseEntity<?> getUserDetailsById(@RequestParam("id") Integer id){
        logger.debug("getUserDetails api got triggered for id={}", id);
        UserDTO user = userService.findById(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping(value = "/getUserDetailsByLoginId")
    public ResponseEntity<?> getUserDetailsByLoginId(@RequestParam("loginId") String loginId){
        logger.debug("getUserDetailsByLoginId api got triggered for loginId={}", loginId);
        UserDTO user = userService.findByLoginId(loginId);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping(value = "/searchUser")
    public ResponseEntity<?> searchUser(@RequestParam("userName") String userName){
        logger.debug("searchUser api got triggered for userName={}", userName);
        List<UserDTO> users = userService.searchUser(userName);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }



}
