package fr.excilys.controller;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import fr.excilys.services.UserInfoService;

@Controller
public class Login {
	private UserInfoService userInfoService;
	
	public Login(UserInfoService userInfoService) {
		System.out.println("LOGIN CONSTRUCTEUR");
		this.userInfoService = userInfoService;
	}
	
	@RequestMapping(value = "/Login")
	public String LoginPage() {
		System.out.println("LOGINPAGE");
		//ModelAndView modelAndView = new ModelAndView("redirect:/Login");
		return "/Login";
	}
	
	@RequestMapping(value = "/403", method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView accessDenied(Principal principal) {

        ModelAndView modelAndView = new ModelAndView("403");
        if (principal != null) {
            modelAndView.addObject("message", "Oups" + principal.getName()
                    + ". Faut venir acompagné de 1 meuf au moins poto");
        } else {
            modelAndView.addObject("message","Access denied!");
        }
        return modelAndView;
    }
}
