package elecciones2021.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class UserController {
    @RequestMapping(value = {"/", "/application"}, method = RequestMethod.GET)
    public String welcome(Model model) {
        return "application";
    }

    @RequestMapping(value = "/dashboard", method = RequestMethod.GET)
    public String redirect() {
    	return "dashboard";
        //return "redirect:/resources/static/dashboard/index.html";
    }
    @RequestMapping(value = {"/login","/login"}, method = RequestMethod.GET)
    public String login(Model model) {
        return "login";
    }
}
