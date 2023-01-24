package com.jdbc.web;

import com.jdbc.dao.UserDAOImpl;
import com.jdbc.model.User;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
public class CustController {

    private final UserDAOImpl userDAO;

    public CustController(UserDAOImpl userDAO) {
        this.userDAO = userDAO;
    }

    @GetMapping("/hello")
    public ModelAndView hello(ModelAndView model) {
        model.setViewName("hello");
        model.addObject(new User());
        return model;
    }

    @PostMapping("/add-user")
    @ResponseBody
    public ModelAndView addUser(    @RequestParam("firstName") String firstName,
                                    @RequestParam("lastName") String lastName,
                                    ModelAndView model) {
        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        userDAO.save(user);
        model.setViewName("result");
        model.addObject(user);
        return model;
    }

    @GetMapping("/getall")
    public ModelAndView getAllUsers(ModelAndView model) {
        model.setViewName("allUsers");
        List<User> users = userDAO.getAll();
        model.addObject(users);
        return model;
    }
}
