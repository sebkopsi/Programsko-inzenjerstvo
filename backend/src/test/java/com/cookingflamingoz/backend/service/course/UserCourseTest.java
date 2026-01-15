package com.cookingflamingoz.backend.service.course;

import com.cookingflamingoz.backend.controller.course.CourseRequests;
import com.cookingflamingoz.backend.controller.user.LoginRequest;
import com.cookingflamingoz.backend.model.Course;
import com.cookingflamingoz.backend.model.User;
import com.cookingflamingoz.backend.repository.CourseRepository;
import com.cookingflamingoz.backend.repository.CourseTagRepository;
import com.cookingflamingoz.backend.repository.TagRepository;
import com.cookingflamingoz.backend.repository.UserRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.FieldSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class UserCourseTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private EntityManager entityManager;

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private TagRepository tagRepository;

    @Mock
    private CourseTagRepository courseTagRepository;

    @InjectMocks
    private CourseService courseService;

    static List<Arguments> validGetByIdRequests =  List.of(
            Arguments.of(999, 1),
            Arguments.of(1, 999)
    );
    static List<Arguments> invalidGetByIdRequests =  List.of(
            Arguments.of(999, 1),
            Arguments.of(1, 999)
    );

    @ParameterizedTest
    @FieldSource("invalidGetByIdRequests")
    void shouldReturnFailGetById(int id, int userId) {
        when(courseRepository.findById(1)).thenReturn(Optional.of(new Course()));
        when(userRepository.findById(1)).thenReturn(Optional.of(new User()));
        var response = courseService.getById(id, userId);
        assertFalse(response.success);
    }

    @ParameterizedTest
    @FieldSource("validGetByIdRequests")
    void shouldReturnSuccessGetById(int id, int userId) {
        var user = new User();
        user.setUserId(1);
        var course = new Course();
        course.setTags(new HashSet<>());
        course.setCreator(user);
        when(courseRepository.findById(anyInt())).thenReturn(Optional.of(course));
        when(userRepository.findById(anyInt())).thenReturn(Optional.of(new User()));
        var response = courseService.getById(id, userId);
        assertTrue(response.success);
    }

    // ------------------------------------- To make it more readable :) -------------------------------------

    static List<Arguments> validCreateCourseRequest =  List.of(
            Arguments.of(1, new CourseRequests.CreateRequest("name", "desc", new HashSet<>()))
    );

    static List<Arguments> invalidCreateCourseRequest =  List.of(
            Arguments.of(1, new CourseRequests.CreateRequest("", "desc", new HashSet<>())),
            Arguments.of(1, new CourseRequests.CreateRequest("name", "", new HashSet<>())),
            Arguments.of(null, new CourseRequests.CreateRequest("name", "desc", new HashSet<>())),
            Arguments.of(1, null),
            Arguments.of(99, new CourseRequests.CreateRequest("name", "desc", new HashSet<>()))
    );


    @ParameterizedTest
    @FieldSource("validCreateCourseRequest")
    void shouldReturnSuccessCreate(Integer id, CourseRequests.CreateRequest request) {
        when(courseRepository.findById(1)).thenReturn(Optional.of(new Course()));
        when(userRepository.findById(1)).thenReturn(Optional.of(new User()));
        var response = courseService.create(id, request);
        assertTrue(response.success);
    }

    @ParameterizedTest
    @FieldSource("invalidCreateCourseRequest")
    void shouldReturnFailCreate(Integer id, CourseRequests.CreateRequest request) {
        when(courseRepository.findById(1)).thenReturn(Optional.of(new Course()));
        when(userRepository.findById(1)).thenReturn(Optional.of(new User()));
        var response = courseService.create(id, request);
        assertFalse(response.success);
    }

}
