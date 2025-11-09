package com.cookingflamingoz.backend.service;

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

    private final PasswordEncoder passwordEncoder;

    // Constructor-based dependency injection (recommended)
    public UserService(UserRepository userRepository, EnrolleeProfileRepository enrolleeProfileRepository, DifficultyLevelRepository difficultyLevelRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.enrolleeProfileRepository = enrolleeProfileRepository;

        this.difficultyLevelRepository = difficultyLevelRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Create a user - here uses argon2 hashing
    // used for signup
    public UserCreationResult saveUser(UserEnrolleeDTO dto) {

        //check if email is ok
        String emailRegex = "^[A-Za-z0-9._%+\\-]+@[A-Za-z0-9.\\-]+\\.[A-Za-z]{2,}$";
        String email = dto.getEmail().trim().toLowerCase(); // NOTE: toLowercase is ok, usually, also note note, if change , do same in login
        if (!java.util.regex.Pattern.matches(emailRegex, email)){
            return UserCreationResult.failure("Email format not valid!");
        }
        User user = userRepository.findByEmail(dto.getEmail());

        if(user == null){ // non existing email

            //check if username is ok
            String usernameRegex = "^[A-Za-z0-9._\\-]{3,50}$";
            String username = dto.getUsername().trim();
            if (!java.util.regex.Pattern.matches(usernameRegex, username)){
                return UserCreationResult.failure("Username not valid! - min 3, max 50, alphanum + {-,.,_}");
            }
            // check if username existing
            if(enrolleeProfileRepository.findByUsername(dto.getUsername()) != null){
                return UserCreationResult.failure("Username already exists!");
            }

            //check if password is ok
            String pass = dto.getPassword();
            if(pass == null || pass.length() < 12){
                return UserCreationResult.failure("Password too short, minimum is 12 characters!");
            }
            if(pass.length() > 1024){
                return UserCreationResult.failure("Password too big, maximum is 1024 characters!");
            }
            if(pass.indexOf('\0') >= 0){
                return UserCreationResult.failure("Password contains invalid characters! (\\0)");
            }

            // TODO: remove this, make levels be static in DB
            //ensures existance of dificulty level
            DifficultyLevel dl = difficultyLevelRepository.getByName("beginner");
            if (dl == null) { // level not existing -> create it
                DifficultyLevel beginnerLevel = new DifficultyLevel(null, "beginner");
                dl = difficultyLevelRepository.save(beginnerLevel);
            }



            EnrolleeProfile enrolleeProfile = new EnrolleeProfile(null,
                    username,
                    dl.getDiffidultyId());
            enrolleeProfile = enrolleeProfileRepository.save(enrolleeProfile);

            //password hashing
            String hashedPassword = passwordEncoder.encode(dto.getPassword());

            //creating user
            user = new User(null,
                    dto.getFirstname(),
                    dto.getSurname(),
                    hashedPassword,
                    email,
                    new Date(),
                    enrolleeProfile.getEnrolleeId(),
                    null);
            user = userRepository.save(user);
            return UserCreationResult.success(user);
        }
        else { // email existing
            return UserCreationResult.failure("Email already in use!");
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

    // user provides either username (EnrolleeProfile.username) or email (User.email) + password
    // gets logged in if correct credentials provided
    // TODO: sessions
    public UserCreationResult getUserByUsernameOrEmail(String usernameOrEmail, String password) {
        User b;

        if (usernameOrEmail == null || password == null) {
            return UserCreationResult.failure("Missing credentials");
        }
        String input = usernameOrEmail.trim();
        if (input.isEmpty() || input.length() > 254) {
            return UserCreationResult.failure("Invalid username or email");
        }
        if (input.indexOf('\0') >= 0) {
            return UserCreationResult.failure("Invalid characters in input");
        }
        if(input.contains("@")) { // email
            String emailRegex = "^[A-Za-z0-9._%+\\-]+@[A-Za-z0-9.\\-]+\\.[A-Za-z]{2,}$";
            if (!java.util.regex.Pattern.matches(emailRegex, input)) {
                return UserCreationResult.failure("Invalid email format");
            }
            input = input.toLowerCase(); // NOTE: toLowercase is ok, usually, also note note, if change , do same in login
            b = userRepository.findByEmail(input);
            if(b == null){
                return UserCreationResult.failure("Email: " + usernameOrEmail + " not found!");
            }
        } else { // username
            //allow alphanum + '-', '_', '.' -> length [3 - 50]
            String usernameRegex = "^[A-Za-z0-9._\\-]{3,50}$";
            if (!java.util.regex.Pattern.matches(usernameRegex, input)) {
                return UserCreationResult.failure("Invalid username format"); // TODO: do it in signup
            }
            EnrolleeProfile a = enrolleeProfileRepository.findByUsername(input);
            System.out.println("Found enrollee profile: " + a);
            if (a != null) {
                b = userRepository.findByEnrolleeId(a.getEnrolleeId());
            } else { // didnt find that username
                return UserCreationResult.failure("Username: " + input + " not found!");
            }


        }
        if (b == null) { // this should not happen (if we have enrolleeProfile we should have User linked to it), or if user prompted email, than also we should have user
            return UserCreationResult.failure("ERROR in UserService: User with username " + input + " not found!");
        }
        // check password
        // NOTE: not sure if needed
        if (password.isEmpty()) {
            return UserCreationResult.failure("Missing credentials");
        }
        if (password.indexOf('\0') >= 0) { // TODO: check this? -> if no update signup as well
            return UserCreationResult.failure("Invalid characters in password");
        }

        if(passwordEncoder.matches(password, b.getPassword())){
            return UserCreationResult.success(b);
        } else {
            return UserCreationResult.failure("Invalid password for user: " + usernameOrEmail);
        }

    }
}

