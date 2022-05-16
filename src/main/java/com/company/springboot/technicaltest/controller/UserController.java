package com.company.springboot.technicaltest.controller;


import com.company.springboot.technicaltest.exception.ConstraintException;
import com.company.springboot.technicaltest.exception.ResourceNotFoundException;
import com.company.springboot.technicaltest.model.User;
import com.company.springboot.technicaltest.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }


    /**
     * Gets all the registered users
     * @return
     */
    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }


    /**
     * Gets a user by its id
     * @param userId
     * @return
     * @throws ResourceNotFoundException
     */
    @GetMapping("/users/{id}")
    public ResponseEntity < User > getUserById(@PathVariable(value = "id") Long userId)
            throws ResourceNotFoundException {
        User user = userService.getUserById(userId).orElseThrow();
        return ResponseEntity.ok().body(user);
    }


    /**
     * Creates a user from information passed inside the body of the request
     * @param user
     * @return
     * @throws ConstraintException
     */
    @PostMapping("/users")
    public ResponseEntity < Object > createUser(@Validated @RequestBody User user) throws ConstraintException {
            User userCreated = userService.createUser(user);
            return ResponseEntity.ok().body(userCreated);
    }


    /**
     * Updates user information by specifying its id
     * @param userId
     * @param userDetails
     * @return
     * @throws ResourceNotFoundException
     */
    @PutMapping("/users/{id}")
    public ResponseEntity < User > updateUser(@PathVariable(value = "id") Long userId,
                                                      @Validated @RequestBody User userDetails) throws ResourceNotFoundException {
        User updatedUser = userService.updateUser(userId, userDetails);
        return ResponseEntity.ok(updatedUser);
    }


    /**
     * Deletes user information by specifying its id
     * @param userId
     * @return
     * @throws ResourceNotFoundException
     */
    @DeleteMapping("/users/{id}")
    public Map< String, Boolean > deleteUser(@PathVariable(value = "id") Long userId)
            throws ResourceNotFoundException {
        return userService.deleteUser(userId);
    }


}
