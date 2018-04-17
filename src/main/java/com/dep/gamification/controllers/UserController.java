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

    // this method is just for me because I do not want to insert users manually in db
    @PostMapping("/creation")
    public ResponseEntity<String> createUser(@RequestBody User user) {
        if (user != null) {
            userService.createUser(user);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }
        return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).build();
    }

    // TODO: modify the path in frontend
    @CrossOrigin( origins = "http://localhost:4200")
    @GetMapping("/status")
    public @ResponseBody ResponseEntity<List<UserStatus>> getStatusForAllUsers(){
        List<UserStatus> users = userService.getStatusForAllUsers();
        if(users != null){
            return new ResponseEntity<>(users, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // TODO: modify the path in frontend
    @GetMapping("/status/{userId}")
    public @ResponseBody ResponseEntity<UserDto> getStatusByUserId(@PathVariable String userId){
        UserDto userDto = null;
        //if(userId != null){
            List<User> users = userService.getAllUser();
            for(User user : users){
                if(user.getUserId().equals(userId)){
                    userDto = userService.getStatusByUserId(userId);
                    return new ResponseEntity<UserDto>(userDto, HttpStatus.OK);
                }
            }
            return new ResponseEntity<UserDto>(userDto, HttpStatus.NOT_FOUND);
       // }
       // return new ResponseEntity<UserDto>(userDto, HttpStatus.BAD_REQUEST);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/beneficiaries")
    public @ResponseBody ResponseEntity<List<String>> getAllBeneficiariesName(){
        List<String> beneficiariesName = userService.getAllBeneficiariesName();
        if(beneficiariesName !=null){
            return new ResponseEntity<>(beneficiariesName, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
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

}
