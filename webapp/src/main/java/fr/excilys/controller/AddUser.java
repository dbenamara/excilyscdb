package fr.excilys.controller;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import fr.excilys.dto.UserInfoDto;
import fr.excilys.mapper.UserInfoMapper;
import fr.excilys.services.UserInfoService;

@Controller
public class AddUser {
	@Autowired
    private final UserInfoService userInfoService;

    public AddUser(UserInfoService userInfoService) {
        this.userInfoService = userInfoService;
    }

    @GetMapping
    public ModelAndView register() {
        return new ModelAndView("addUser");
    }

    @PostMapping
    @Transactional
    public ModelAndView register(UserInfoDto userInfoDto) {
        ModelAndView modelAndView = new ModelAndView("redirect:/dashboard");

        userInfoService.create(UserInfoMapper.fromUserInfoDtotoUserInfo(userInfoDto));

        return modelAndView;
    }

	
}
