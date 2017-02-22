package com.ironyard.controller.mvc;

import com.ironyard.data.HttpInfo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

/**
 * Created by wailm.yousif on 2/16/17.
 */

@Controller
@RequestMapping(path = "/secure/info")
public class InfoController
{
    @RequestMapping(path = "/show", method = RequestMethod.GET)
    private String show(HttpServletRequest request, Model dataModel)
    {
        HttpInfo httpInfo = new HttpInfo();

        Enumeration headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements())
        {
            String key = (String) headerNames.nextElement();
            String value = request.getHeader(key);
            httpInfo.addHttpHeader(key, value);
        }

        dataModel.addAttribute("httpInfoMap", httpInfo.getMap());

        return "/secure/httpinfo";
    }
}
