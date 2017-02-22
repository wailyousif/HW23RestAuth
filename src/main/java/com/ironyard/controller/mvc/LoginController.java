package com.ironyard.controller.mvc;

import com.ironyard.data.ChatUser;
import com.ironyard.data.UserPermission;
import com.ironyard.repo.ChatUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by jasonskipper on 2/6/17.
 */
@Controller
public class LoginController {

    @Autowired
    private ChatUserRepo chatUserRepo;


    @RequestMapping(path = "/authenticate", method = RequestMethod.POST)
    public String login(HttpServletRequest request, Model data, @RequestParam(name = "username") String usr, @RequestParam String password)
    {
        addSuperUserForSimulation();

        ChatUser found = chatUserRepo.findByUsernameAndPassword(usr, password);
        String destinationView = "/secure/home";
        if(found == null)
        {
            data.addAttribute("notify", 1);
            data.addAttribute("notifyMsg", "Invalid credentials!");
            destinationView = "/open/login";
        }
        else
        {
            data.addAttribute("notify", 0);
            request.getSession().setAttribute ("chatUser", found);

            destinationView = "redirect:/secure/msgboards/show";
        }
        return destinationView;
    }


    private void addSuperUserForSimulation()
    {
        String superUser_Username = "wail";
        String superUser_Password = "wail";
        String superUser_DisplayName = "Wail M. Yousif";

        ChatUser found = chatUserRepo.findByUsernameAndPassword(superUser_Username, superUser_Username);
        if (found == null)
        {
            UserPermission permission = new UserPermission(true, true, true);
            ChatUser chatUser = new ChatUser(superUser_Username, superUser_Username,
                    superUser_DisplayName, permission);

            chatUserRepo.save(chatUser);
        }
    }


    @RequestMapping(path = "/logout", method = RequestMethod.GET)
    public String logout(HttpServletRequest request)
    {
        request.getSession().invalidate();
        return "redirect:/secure";
    }
}
