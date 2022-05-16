package com.company.springboot.technicaltest.service;

import com.company.springboot.technicaltest.exception.ConstraintException;
import com.company.springboot.technicaltest.exception.ResourceNotFoundException;
import com.company.springboot.technicaltest.model.User;
import com.company.springboot.technicaltest.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }


    /**
     * Gets all the registered users
     * @return
     */
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    /**
     * Gets a user by its id
     * @param userId
     * @return
     * @throws ResourceNotFoundException
     */
    public Optional<User> getUserById(Long userId) throws ResourceNotFoundException {
        return userRepository.findById(userId);
    }


    /**
     * Creates a user after checking if all constraints are respected
     * @param user
     * @return
     * @throws ConstraintException
     */
    public User createUser(User user) throws ConstraintException {
        if(checkCreateUserConstraints(user)) {
            return userRepository.save(user);
        }else{
            throw new ConstraintException("You are not allowed to create a count");
        }
    }

    /**
     * Checks user registration constraints
     * @param user object that contains user information
     * @return
     * @throws ConstraintException
     */
    private boolean checkCreateUserConstraints(User user) throws ConstraintException {
        if(!isAdult(user)) throw new ConstraintException("Your age does not allow you to register");
        if(!isFrenchResident(user)) throw new ConstraintException("Your Country does not allow you to register");
        if(isUserExists(user)) throw new ConstraintException("User already exists");
        return true;
    }

    /*####################### Constraints for User Creation #######################*/

    /**
     * Checks if the user is French resident
     */
    private Boolean isFrenchResident(User user){
        return user.getCountryOfResidence().equalsIgnoreCase("france");
    }

    /**
     * Checks if the user is adult
     */
    private Boolean isAdult(User user){
        LocalDate birthday = user.getBirthday();
        LocalDate currentDate = LocalDate.now();
        return Period.between(birthday, currentDate).getYears() > 18;
    }

    private Boolean isUserExists(User user){
        return userRepository.findAll().stream(). anyMatch( e -> e.getName().equalsIgnoreCase(user.getName()) && e.getBirthday().equals( user.getBirthday()) && e.getCountryOfResidence().equalsIgnoreCase(e.getCountryOfResidence()));
    }

    /*############################################################################*/


    /**
     * Updates user information by specifying its id
     * @param userId
     * @param userDetails
     * @return
     * @throws ResourceNotFoundException
     */
    public User updateUser(Long userId, User userDetails) throws ResourceNotFoundException {
        User userToUpdate = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found for this id :: "+ userId ));

        userToUpdate.setName(userDetails.getName());
        userToUpdate.setBirthday(userDetails.getBirthday());
        userToUpdate.setCountryOfResidence(userDetails.getCountryOfResidence());
        userToUpdate.setPhoneNumber(userDetails.getPhoneNumber());
        userToUpdate.setGender(userDetails.getGender());

        final User updatedUser = userRepository.save(userToUpdate);
        return updatedUser;

    }


    /**
     * Deletes user information by specifying its id
     * @param userId
     * @return
     * @throws ResourceNotFoundException
     */
    public Map<String, Boolean> deleteUser(Long userId) throws ResourceNotFoundException {
        User userToDelete = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found for this id :: "+ userId ));

        userRepository.delete(userToDelete);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }
}
