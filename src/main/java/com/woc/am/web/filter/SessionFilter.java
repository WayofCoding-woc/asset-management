package com.woc.am.web.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class SessionFilter implements Filter {
    private static final Logger logger = LoggerFactory.getLogger(SessionFilter.class);

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpSession session = httpServletRequest.getSession(false);
        if(null != session && !session.isNew()){
            logger.debug("session.id={}", session.getId());
            filterChain.doFilter(servletRequest, servletResponse);
        }else{
            logger.debug("session does not exist");
            HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;

            if (isAjax(httpServletRequest)) {
                //httpServletResponse.getWriter().print(xmlPartialRedirectToPage(httpServletRequest, "/session_expired.html"));
                //httpServletResponse.flushBuffer();
                httpServletResponse.setStatus(HttpServletResponse.SC_TEMPORARY_REDIRECT);
            }else{
                httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + "/session_expired.html");
            }

        }
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
