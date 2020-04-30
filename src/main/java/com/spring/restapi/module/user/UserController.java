package com.spring.restapi.module.user;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/users")
public class UserController {

  @Autowired UserService userService;

  @GetMapping("/")
  public List<User> getAllUser() {
    return userService.getAllUser();
  }

  @PostMapping("/")
  public User getGeneratedUser(@RequestBody User user) {
    return userService.getGeneratedUser(user);
  }

  @PutMapping("/{id}")
  public User getUpdatedUser(@PathVariable("id") Long id, @RequestBody User user) {
    return userService.getUpdatedUser(id, user);
  }

  @DeleteMapping("/{id}")
  public void deleteUser(@PathVariable("id") Long id) {
    userService.deleteUser(id);
  }
}
