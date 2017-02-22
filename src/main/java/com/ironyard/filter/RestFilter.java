package com.ironyard.filter;

import com.ironyard.data.ChatToken;
import com.ironyard.repo.ChatTokenRepo;
import com.ironyard.security.TokenMaster;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by wailm.yousif on 2/21/17.
 */

@WebFilter(filterName = "RestFilter")
public class RestFilter implements Filter{

    @Autowired
    ChatTokenRepo chatTokenRepo;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException
    {
        // we need to look for authorization token and validate it
        HttpServletResponse res = (HttpServletResponse) servletResponse;
        HttpServletRequest req = (HttpServletRequest) servletRequest;

        // check for key based authentication
        boolean authorized = false;
        String authToken = req.getHeader("x-authorization-key");
        if(authToken != null)
        {
            TokenMaster tm = new TokenMaster();
            Long userId = tm.validateTokenAndGetUserId(authToken);
            if (userId != null)
            {
                //System.out.println("Before printing");

                ChatToken dbChatToken = chatTokenRepo.findByTokenString(authToken);
                dbChatToken.setNumOfCalls(dbChatToken.getNumOfCalls()+1);
                chatTokenRepo.save(dbChatToken);

                req.setAttribute("userId", userId);
                filterChain.doFilter(req, res);
            }
            else
            {
                res.getWriter().write("Invalid Authorization. Go Away.");
            }
        }
        else
        {
            res.getWriter().write("Invalid Authorization. Go Away.");
        }
    }

    @Override
    public void destroy() {

    }
}