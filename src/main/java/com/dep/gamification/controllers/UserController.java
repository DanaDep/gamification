package com.dep.gamification.controllers;

import com.dep.gamification.models.User;
import com.dep.gamification.services.UserService;
import com.dep.gamification.util.UserDto;
import com.dep.gamification.util.UserStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired private UserService userService;

    @PostMapping("/creation")
    public ResponseEntity<String> createUser(@RequestBody User user) {
        if (user != null) {
            userService.createUser(user);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }
        return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).build();
    }

    @GetMapping("/")
    public @ResponseBody ResponseEntity<List<UserStatus>> getStatusForAllUsers(){
        List<UserStatus> users = userService.getStatusForAllUsers();
        if(users != null){
            return new ResponseEntity<>(users, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserByUserId(@PathVariable("userId") String userId) {
        User user = null;
        if (userId != null) {
            user = userService.getUserByUserId(userId);
            return new ResponseEntity<User>(user, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{userId}/status")
    public @ResponseBody ResponseEntity<UserDto> getStatusByUserId(@PathVariable String userId){
        UserDto userDto = null;
        if(userId != null){
            userDto = userService.getStatusByUserId(userId);
            return new ResponseEntity<UserDto>(userDto, HttpStatus.OK);
        }
        return new ResponseEntity<UserDto>(userDto, HttpStatus.NOT_FOUND);
    }

}
