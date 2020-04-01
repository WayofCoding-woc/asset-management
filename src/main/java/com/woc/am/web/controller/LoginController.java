package com.woc.am.web.controller;

import com.woc.am.dto.LoginCredentialsDTO;
import com.woc.am.dto.UserDTO;
import com.woc.am.service.LoginService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/api")
public class LoginController {
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private LoginService loginService;

    @PostMapping(value = "/isValidUser", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> isValidUser(@RequestBody LoginCredentialsDTO loginCredentials){
        logger.info("isValidUser api got triggered, loginCredentials={}", loginCredentials);
        boolean validUser = loginService.isValidUser(loginCredentials);
        return new ResponseEntity<>(validUser, HttpStatus.OK);
    }

    @PostMapping(value = "/doLogin", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> doLogin(@RequestBody LoginCredentialsDTO loginCredentials, HttpServletRequest request){
        logger.debug("doLogin api got triggered");
        UserDTO user = loginService.doLogin(loginCredentials);

        Map<String, Object> data = new HashMap<>();

        if(null == user){
            data.put("invalidCredentials", true);
            return new ResponseEntity<>(data, HttpStatus.OK);
        }
        if(!user.isActive()){
            data.put("inActiveUser", true);
            return new ResponseEntity<>(data, HttpStatus.OK);
        }

        HttpSession session = request.getSession();
        session.setAttribute("loginId", user.getLoginId());
        session.setAttribute("userName", user.getUserName());
        session.setAttribute("id", user.getId());
        session.setAttribute("role", user.getRole());

        data.put("userName", user.getUserName());
        data.put("session", session.getId());
        data.put("id", user.getId());
        data.put("role", user.getRole());

        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    @GetMapping(value = "/doLogout")
    public ModelAndView doLogout(HttpServletRequest request){
        logger.debug("doLogout api got triggered");
        HttpSession session = request.getSession(false);
        if(null != session){
            session.invalidate();
        }

        return new ModelAndView("redirect:./../logout.html");
    }

    @PostMapping(value = "/resetPassword")
    public ModelAndView resetPassword(HttpServletRequest request){
        String loginId = request.getParameter("loginId");
        String old_password = request.getParameter("old_password");
        String new_password = request.getParameter("new_password");

        logger.info("resetPassword api got triggered for loginId={}", loginId);

        try{
            loginService.resetPassword(loginId, old_password, new_password);
        }catch (Exception e){
            return new ModelAndView("redirect:./../error.html");
        }

        return new ModelAndView("redirect:./../reset_password_success.html");
    }

    @GetMapping("/getAllOperation")
    public ResponseEntity<?> getAllOperation(@RequestParam("login_id") String loginId){
        Set<Integer> allOperationIds = loginService.getAllOperation(loginId);
        return new ResponseEntity<>(allOperationIds, HttpStatus.OK);
    }
}
