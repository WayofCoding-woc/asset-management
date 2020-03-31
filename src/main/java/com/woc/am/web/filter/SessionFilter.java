package com.woc.am.web.filter;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class SessionFilter implements Filter {
    private static final Logger logger = LoggerFactory.getLogger(SessionFilter.class);

    private static final String API_USER_GET_USER_DETAILS_BY_ID = "/api/user/getUserDetailsById";
    private static final String API_USER_SEARCH_USER = "/api/user/searchUser";
    private static final String API_USER_SESSION_EXISTS = "/api/user/sessionExists";
    private static final Set<String> searchUsers = Set.of(API_USER_GET_USER_DETAILS_BY_ID, API_USER_SEARCH_USER);
    private static final Set<String> registerUsers = Set.of("/api/user/registerUser");
    private static final Set<String> manageAssets = Set.of("/api/asset/createAsset",
                                                            "/api/asset/searchAsset",
                                                            "/api/asset/allocate",
                                                            "/api/asset/deallocate",
                                                            "/api/asset/audit");

    private static final Set<String> user_role_has_privileges = new HashSet<>(){{
        add(API_USER_SESSION_EXISTS);
        add(API_USER_GET_USER_DETAILS_BY_ID);
    }};

    private static final Set<String> support_role_has_privileges = new HashSet<>(){{
        add(API_USER_SESSION_EXISTS);
        addAll(searchUsers);
        addAll(registerUsers);
    }};

    private static final Set<String> admin_role_has_privileges = new HashSet<>(){{
        add(API_USER_SESSION_EXISTS);
        addAll(searchUsers);
        addAll(registerUsers);
        addAll(manageAssets);
    }};

    private static final String USER_ROLE = "user";
    private static final String SUPPORT_ROLE = "support";
    private static final String ADMIN_ROLE = "admin";

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;

        HttpSession session = httpServletRequest.getSession(false);
        if(null != session && !session.isNew()){
            logger.debug("session.id={}", session.getId());

            boolean userUnAuthorized = isUserUnAuthorized(servletRequest, servletResponse, httpServletRequest, session);
            if(userUnAuthorized){
                if (isAjax(httpServletRequest)) {
                    httpServletResponse.setStatus(HttpServletResponse.SC_TEMPORARY_REDIRECT);
                }else{
                    httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + "/unauthorized_access.html");
                }
            }else{
                filterChain.doFilter(servletRequest, servletResponse);
            }
        }else{
            logger.debug("session does not exist");

            if (isAjax(httpServletRequest)) {
                //httpServletResponse.getWriter().print(xmlPartialRedirectToPage(httpServletRequest, "/session_expired.html"));
                //httpServletResponse.flushBuffer();
                httpServletResponse.setStatus(HttpServletResponse.SC_TEMPORARY_REDIRECT);
            }else{
                httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + "/session_expired.html");
            }

        }
    }

    private boolean isUserUnAuthorized(ServletRequest servletRequest, ServletResponse servletResponse, HttpServletRequest httpServletRequest, HttpSession session) throws IOException {
        String requestURI = httpServletRequest.getRequestURI();
        logger.debug("uri="+ requestURI);

        String loggedInUserRole = (String)session.getAttribute("role");
        if(StringUtils.isBlank(loggedInUserRole)){
            return true;
        }

        if(USER_ROLE.equals(loggedInUserRole)){
            if(!user_role_has_privileges.contains(requestURI)){
                return true;
            }
            if(!API_USER_SESSION_EXISTS.equals(requestURI)){
                String requestedUserId = httpServletRequest.getParameter("id");
                Integer loggedInUserId = (Integer)session.getAttribute("id");
                if(!requestedUserId.equals(String.valueOf(loggedInUserId))){
                    return true;
                }
            }
        } else if(SUPPORT_ROLE.equals(loggedInUserRole) && !support_role_has_privileges.contains(requestURI)){
            return true;
        } else if(ADMIN_ROLE.equals(loggedInUserRole) && !admin_role_has_privileges.contains(requestURI)){
            return true;
        }

        return false;
    }

    private String xmlPartialRedirectToPage(HttpServletRequest request, String page) {
        StringBuilder sb = new StringBuilder();
        sb.append("<?xml version='1.0' encoding='UTF-8'?>");
        sb.append("<partial-response><redirect url=\"").append(request.getContextPath()).append(page).append("\"/></partial-response>");
        String actualUrl = sb.toString();
        logger.info("actualUrl="+actualUrl);
        return actualUrl;
    }

    private boolean isAjax(HttpServletRequest request) {
        return "XMLHttpRequest".equals(request.getHeader("X-Requested-With"));
    }
}
