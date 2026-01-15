package com.cookingflamingoz.backend.service.user;
import com.cookingflamingoz.backend.controller.user.LoginRequest;
import com.cookingflamingoz.backend.controller.user.SignUpRequest;
import com.cookingflamingoz.backend.model.User;
import com.cookingflamingoz.backend.repository.*;

import org.junit.jupiter.api.extension.ExtendWith;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.FieldSource;
import org.junit.jupiter.params.provider.NullSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.cookingflamingoz.backend.model.*;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private TagRepository tagRepository;

    @Mock
    private UserTagRepository userTagRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private EnrolleeProfileRepository enrolleeProfileRepository;

    @Mock
    private DifficultyLevelRepository difficultyLevelRepository;

    @InjectMocks
    private UserService userService;


    static List<LoginRequest> invalidLoginRequests = List.of(
             new LoginRequest()
            ,new LoginRequest("email", "")
            ,new LoginRequest("email", "")
            ,new LoginRequest("", "pass")
            ,new LoginRequest("email", "password")
            ,new LoginRequest("email@", "password")
            ,new LoginRequest("invalid@mail.com", "password")
            ,new LoginRequest("invalidinvalidinvalidinvalidinvalidinvalidinvalidinvalidinvalidinvalidinvalidinvalidinvalidinvalidinvalidinvalidinvalidinvalidinvalidinvalidinvalidinvalidinvalidinvalidinvalidinvalidinvalidinvalidinvalidinvalidinvalidinvalidinvalidinvalidinvalidinvalidinvalidinvalidinvalidinvalidinvalidinvalidinvalidinvalidinvalidinvalidinvalidinvalidinvalidinvalidinvalidinvalidinvalidinvalid@mail.com", "password")
    );

    static List<LoginRequest> validLoginRequests = List.of(
            new LoginRequest("test@email.com", "password123")
    );



    @ParameterizedTest
    @FieldSource("invalidLoginRequests")
    void shouldReturnFailureLogin(LoginRequest request) {
        var response = userService.Login(request);
        assertFalse(response.isSuccess());
    }

    @ParameterizedTest
    @FieldSource("validLoginRequests")
    void shouldReturnSuccess(LoginRequest request) {
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(true);
        when(userRepository.findByEmail(request.getEmail())).thenReturn(
                new User(1, "", "", request.getPassword(), request.getEmail(), new Date(), 1, null)
        );

        var response = userService.Login(request);
        assertTrue(response.isSuccess());
    }

// ------------------------------------- To make it more readable :) -------------------------------------

    static List<SignUpRequest> invalidSignupRequests = List.of(
            new SignUpRequest("username", "", "test", "password123456790",  "test@testmail.com")
            ,new SignUpRequest("username", "test", "", "password123456790",  "test@testmail.com")
            ,new SignUpRequest("username", "test", "test", "",  "test@testmail.com")
            ,new SignUpRequest("username", "test", "test", "password123456790",  "")
            ,new SignUpRequest("s", "test", "test", "password123456790",  "test@testmail.com")
            ,new SignUpRequest("toolongtoolongtoolongtoolongtoolongtoolongtoolongtoolong", "test", "test", "password123456790",  "test@testmail.com")
            ,new SignUpRequest("username", "test", "test", "password123456790",  "taken@testmail.com")
            ,new SignUpRequest("username", "test", "test", "short",  "taken@testmail.com")
            ,new SignUpRequest("username", "test", "test", "password123456790",  "test@testmail.com")
            );
    static List<SignUpRequest> validSignupRequests = List.of(
            new SignUpRequest("username", "test", "test", "password123456790",  "test@testmail.com")
    );
    @ParameterizedTest
    @FieldSource("invalidSignupRequests")
    void shouldReturnFailureSignup(SignUpRequest request) {
        when(passwordEncoder.encode(anyString())).thenReturn(request.getPassword());
        when(difficultyLevelRepository.getByName(anyString())).thenReturn(new DifficultyLevel(1,"beginner"));
        DifficultyLevel dl = difficultyLevelRepository.getByName("beginner");
        EnrolleeProfile enrolleeProfile = new EnrolleeProfile(null, request.getUsername(), dl.getDiffidultyId());
        when(enrolleeProfileRepository.save(any(EnrolleeProfile.class))).thenReturn(enrolleeProfile);
        when(userRepository.findByEmail(request.getEmail())).thenReturn(
                new User(1, request.getFirstname(), request.getSurname(), request.getPassword(), "taken@testmail.com",
                        new Date(), 1, null)
        );
        when(userRepository.save(any(User.class))).thenReturn(
                new User(1, request.getFirstname(), request.getSurname(), request.getPassword(), request.getEmail(),
                        new Date(), 1, null)
        );


        var response = userService.saveUser(request);
        assertFalse(response.isSuccess());
    }
    @ParameterizedTest
    @FieldSource("validSignupRequests")
    void shouldReturnSuccessSignup(SignUpRequest request) {
        when(passwordEncoder.encode(anyString())).thenReturn(request.getPassword());
        when(difficultyLevelRepository.getByName(anyString())).thenReturn(new DifficultyLevel(1,"beginner"));
        DifficultyLevel dl = difficultyLevelRepository.getByName("beginner");
        EnrolleeProfile enrolleeProfile = new EnrolleeProfile(null, request.getUsername(), dl.getDiffidultyId());
        when(enrolleeProfileRepository.save(any(EnrolleeProfile.class))).thenReturn(enrolleeProfile);
        when(userRepository.findByEmail(anyString())).thenReturn(null);
        when(userRepository.save(any(User.class))).thenReturn(
                new User(1, request.getFirstname(), request.getSurname(), request.getPassword(), request.getEmail(),
                        new Date(), 1, null)
        );


        var response = userService.saveUser(request);
        assertTrue(response.isSuccess());
    }

    // ------------------------------------- To make it more readable :) -------------------------------------

    static List<Arguments> validAddTagRequests =  List.of(
            Arguments.of(1, "alreadyhas", "category", true, true)
            ,Arguments.of(1, "name", "category", true, true)
            ,Arguments.of(1, "name", "category", true, false)
            ,Arguments.of(1, "new", "category", true, true)
    );

    static List<Arguments> invalidAddTagRequests =  List.of(
            Arguments.of(1, "alreadyhas", "category", true, true)
            ,Arguments.of(1, "nonexisting", "category", true, false)
            ,Arguments.of(1, "new", "category", true, true)
            ,Arguments.of(999, "nonexistinguser", "category", true, true)
    );
    @ParameterizedTest
    @FieldSource("invalidAddTagRequests")
    void shouldReturnFailValidTags(int userID, String name, String category, boolean preferred, boolean createNew){
        when(userRepository.existsById(anyInt())).thenReturn(true);
        when(userRepository.existsById(99)).thenReturn(false);
        when(tagRepository.findByNameAndCategory(anyString(),anyString())).thenReturn(new Tag(name, category));
        when(tagRepository.findByNameAndCategory("nonexisting", "category")).thenReturn(null);
        when(tagRepository.save(any(Tag.class))).thenReturn(new Tag("new", category));
        when(userRepository.findById(anyInt())).thenReturn(Optional.empty());


        var response = userService.AddTag(userID, name, category, preferred, createNew);
        assertFalse(response.success);
    }

    // ------------------------------------- To make it more readable :) -------------------------------------

    @ParameterizedTest
    @CsvSource("1")
    void shouldReturnSuccessGetProfile(int userID){
        when(userRepository.findById(anyInt())).thenReturn(Optional.of(new User()));
        var response = userService.GetProfile(userID);
        assertTrue(response.success);
    }

    @ParameterizedTest
    @CsvSource("1")
    void shouldReturnFailGetProfile(int userID){
        when(userRepository.findById(anyInt())).thenReturn(Optional.empty());
        var response = userService.GetProfile(userID);
        assertFalse(response.success);
    }

}
