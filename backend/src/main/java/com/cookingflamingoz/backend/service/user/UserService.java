package com.cookingflamingoz.backend.service.user;

import com.cookingflamingoz.backend.controller.user.SignUpRequest;
import com.cookingflamingoz.backend.model.*;
import com.cookingflamingoz.backend.repository.DifficultyLevelRepository;
import com.cookingflamingoz.backend.repository.EnrolleeProfileRepository;
import com.cookingflamingoz.backend.repository.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Date;

@Service
public class UserService {
    @PersistenceContext
    private EntityManager entityManager;
    private final UserRepository userRepository;
    private final EnrolleeProfileRepository enrolleeProfileRepository;
    private final DifficultyLevelRepository difficultyLevelRepository;
    private final PasswordEncoder passwordEncoder;

    public User findById(int id) {
        return entityManager.find(User.class, id);
    }

    public List<User> findAll() {
        return entityManager.createQuery("SELECT u FROM User u", User.class).getResultList();
    }


    // Constructor-based dependency injection (recommended)
    public UserService(UserRepository userRepository, EnrolleeProfileRepository enrolleeProfileRepository, DifficultyLevelRepository difficultyLevelRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.enrolleeProfileRepository = enrolleeProfileRepository;
        this.difficultyLevelRepository = difficultyLevelRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Create a user - here uses argon2 hashing
    // used for signup
    public UserCreationResult saveUser(SignUpRequest requestData) {
        if (requestData.getEmail() == null || requestData.getPassword() == null ||
            requestData.getFirstname() == null || requestData.getSurname() == null ||
                requestData.getUsername() == null
        ) {
            return UserCreationResult.failure("Missing credentials");
        }
        String email = requestData.getEmail();
        //check if email is ok
        String emailRegex = "^[A-Za-z0-9._%+\\-]+@[A-Za-z0-9.\\-]+\\.[A-Za-z]{2,}$";
        email = email.trim().toLowerCase(); // NOTE: toLowercase is ok, usually, also note note, if change , do same in login
        if (!java.util.regex.Pattern.matches(emailRegex, email)) {
            return UserCreationResult.failure("Email format not valid!");
        }
        User user = userRepository.findByEmail(email); // TODO trebs samo bit String "email" (linija 51)
        if (user != null) { // email existing
            return UserCreationResult.failure("Email already in use!");
        }

        //check if username is ok
        String usernameRegex = "^[A-Za-z0-9._\\-]{3,50}$";
        String username = requestData.getUsername().trim();
        if (!java.util.regex.Pattern.matches(usernameRegex, username)) {
            return UserCreationResult.failure("Username not valid! - min 3, max 50, alphanum + {-,.,_}");
        }
        // check if username existing
        if (enrolleeProfileRepository.findByUsername(requestData.getUsername()) != null) {
            return UserCreationResult.failure("Username already exists!");
        }

        //check if password is ok
        String pass = requestData.getPassword();
        if (pass == null || pass.length() < 12) {
            return UserCreationResult.failure("Password too short, minimum is 12 characters!");
        }
        if (pass.length() > 1024) {
            return UserCreationResult.failure("Password too big, maximum is 1024 characters!");
        }
        if (pass.indexOf('\0') >= 0) {
            return UserCreationResult.failure("Password contains invalid characters! (\\0)");
        }

        DifficultyLevel dl = difficultyLevelRepository.getByName("beginner");

        EnrolleeProfile enrolleeProfile = new EnrolleeProfile(null,
                username,
                dl.getDiffidultyId());
        enrolleeProfile = enrolleeProfileRepository.save(enrolleeProfile);

        //password hashing
        String hashedPassword = passwordEncoder.encode(requestData.getPassword());

        //creating user
        user = new User(null,
                requestData.getFirstname(),
                requestData.getSurname(),
                hashedPassword,
                email,
                new Date(),
                enrolleeProfile.getEnrolleeId(),
                null);

        user = userRepository.save(user);
        if (user.getEmail() != null) {
            return UserCreationResult.success(user);
        }

        return UserCreationResult.failure("Failed to create user");
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

    public UserCreationResult Login(String emailInput, String password) {
        User user;

        if (emailInput == null || password == null) {
            return UserCreationResult.failure("Missing credentials");
        }
        String email = emailInput.trim();
        if (email.isEmpty() || email.length() > 254) {
            return UserCreationResult.failure("Missing email");
        }

        String emailRegex = "^[A-Za-z0-9._%+\\-]+@[A-Za-z0-9.\\-]+\\.[A-Za-z]{2,}$";
        if (!java.util.regex.Pattern.matches(emailRegex, email)) {
            return UserCreationResult.failure("Invalid email format");
        }

        user = userRepository.findByEmail(email);
        if (user == null) {
            return UserCreationResult.failure("Invalid credentials");
        }

        if (passwordEncoder.matches(password, user.getPassword())) {
            return UserCreationResult.success(user);
        } else {
            return UserCreationResult.failure("Invalid credentials");
        }

    }
}

