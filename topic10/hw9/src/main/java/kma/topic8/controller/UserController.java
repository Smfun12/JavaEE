package kma.topic8.controller;

import kma.topic8.model.Role;
import kma.topic8.model.RoleEntity;
import kma.topic8.model.User;
import kma.topic8.repository.RoleRepo;
import kma.topic8.repository.UserRepo;
import lombok.AllArgsConstructor;
import org.assertj.core.util.Lists;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {

    UserRepo userRepo;
    RoleRepo roleRepo;

    @GetMapping("/regForm")
    public String regForm(){
        return "registration";
    }

    @PostMapping("/add")
    public String registerUser(
            @RequestParam(name = "login") String login,
            @RequestParam(name = "password") String password){
        RoleEntity role = roleRepo.findByRole(Role.USER).orElseThrow(() -> new RuntimeException(""));
        User user = User.builder().login(login).password(password)
                .roles(Lists.newArrayList(role)).build();
        userRepo.save(user);
        return "redirect:/login";
    }
}
