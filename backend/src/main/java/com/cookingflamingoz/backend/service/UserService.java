package com.cookingflamingoz.backend.service;

import com.cookingflamingoz.backend.model.DifficultyLevel;
import com.cookingflamingoz.backend.model.EnrolleeProfile;
import com.cookingflamingoz.backend.model.User;
import com.cookingflamingoz.backend.model.UserEnrolleeDTO;
import com.cookingflamingoz.backend.repository.DifficultyLevelRepository;
import com.cookingflamingoz.backend.repository.EnrolleeProfileRepository;
import com.cookingflamingoz.backend.repository.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Date;

@Service
public class UserService{
    @PersistenceContext
    private EntityManager entityManager;

    public User findById(int id) {
        return entityManager.find(User.class, id);
    }

    public List<User> findAll() {
        return entityManager.createQuery("SELECT u FROM User u", User.class).getResultList();
    }


    private final UserRepository userRepository;
    private final EnrolleeProfileRepository enrolleeProfileRepository;
    private final DifficultyLevelRepository difficultyLevelRepository;

    // Constructor-based dependency injection (recommended)
    public UserService(UserRepository userRepository, EnrolleeProfileRepository enrolleeProfileRepository, DifficultyLevelRepository difficultyLevelRepository) {
        this.userRepository = userRepository;
        this.enrolleeProfileRepository = enrolleeProfileRepository;

        this.difficultyLevelRepository = difficultyLevelRepository;
    }

    // Create a user
    // TODO: make is return error if user with email already exists -> will lead to "reset password" feature
    // NOTE: should we hash password here or in controller ?
    // NOTE:  make this return Optional<User> ?
    public User saveUser(UserEnrolleeDTO dto) {
        User user = userRepository.findByEmail(dto.getEmail());
        if(user == null){

            // TODO: remove this, make levels be static in DB
            DifficultyLevel dl = difficultyLevelRepository.getByName("beginner");
            if (dl == null) { // level not existing -> create it
                DifficultyLevel beginnerLevel = new DifficultyLevel(null, "beginner");
                dl = difficultyLevelRepository.save(beginnerLevel);
            }


            // create new user -> firstly create new enrolleeProfile
            EnrolleeProfile enrolleeProfile = new EnrolleeProfile(null,
                    dto.getFirstname() + " " + dto.getSurname(),
                    dl.getDiffidultyId());
            enrolleeProfile = enrolleeProfileRepository.save(enrolleeProfile);

            user = new User(null,
                    dto.getFirstname(),
                    dto.getSurname(),
                    dto.getPassword(),
                    dto.getEmail(),
                    new Date(),
                    enrolleeProfile.getEnrolleeId(),
                    null);
            user = userRepository.save(user);
            return user;
        }
        else {
            return user;
        }

    }

    // Get all users
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Get user by ID
    public Optional<User> getUserById(Integer id) {
        return userRepository.findById(id);
    }

    // Get user by email
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    // Delete user by ID
    public void deleteUser(Integer id) {
        userRepository.deleteById(id);
    }
}

