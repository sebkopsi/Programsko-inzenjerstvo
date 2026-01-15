package com.cookingflamingoz.backend.service.module;

import com.cookingflamingoz.backend.controller.module.ModuleRequests;
import com.cookingflamingoz.backend.model.Course;
import com.cookingflamingoz.backend.model.Module;
import com.cookingflamingoz.backend.model.User;
import com.cookingflamingoz.backend.repository.CourseRepository;
import com.cookingflamingoz.backend.repository.ModuleRepository;
import com.cookingflamingoz.backend.repository.TagRepository;
import com.cookingflamingoz.backend.repository.UserRepository;
import com.cookingflamingoz.backend.service.module.ModuleService;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.FieldSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class ModuleServiceTest {

    @Mock
    private UserRepository userRepository;


    @Mock
    private ModuleRepository moduleRepository;

    @Mock
    private CourseRepository courseRepository;



    @InjectMocks
    private ModuleService moduleService;

    static List<Arguments> validGetByIdRequests =  List.of(
            Arguments.of( 1)
    );
    static List<Arguments> invalidGetByIdRequests =  List.of(
            Arguments.of(999)
    );

    @ParameterizedTest
    @FieldSource("invalidGetByIdRequests")
    void shouldReturnFailGetById(int id) {
        when(moduleRepository.findById(1)).thenReturn(Optional.empty());
        var response = moduleService.getById(id);
        assertFalse(response.success);
    }

    @ParameterizedTest
    @FieldSource("validGetByIdRequests")
    void shouldReturnSuccessGetById(int id) {
        var module = new Module();
        when(moduleRepository.findById(anyInt())).thenReturn(Optional.of(module));
        var response = moduleService.getById(id);
        assertTrue(response.success);
    }

    // ------------------------------------- To make it more readable :) -------------------------------------

    static List<Arguments> validCreateModuleRequest =  List.of(
            Arguments.of(1, new ModuleRequests.CreateRequest("name", 1, 0))
    );

    static List<Arguments> invalidCreateModuleRequest =  List.of(
            Arguments.of(999, new ModuleRequests.CreateRequest("name", 1, 0)),
            Arguments.of(999, null),
            Arguments.of(1, new ModuleRequests.CreateRequest("", 1, 0)),
            Arguments.of(1, new ModuleRequests.CreateRequest("name", null, 0))
    );


    @ParameterizedTest
    @FieldSource("validCreateModuleRequest")
    void shouldReturnSuccessCreate(Integer id, ModuleRequests.CreateRequest request) {
        when(moduleRepository.findById(1)).thenReturn(Optional.of(new Module()));
        var user = new User();
        user.setUserId(1);
        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        var course = new Course();
        course.setCreator(user);
        when(courseRepository.findById(1)).thenReturn(Optional.of(course));

        var response = moduleService.create(id, request);
        assertTrue(response.success);
    }

    @ParameterizedTest
    @FieldSource("invalidCreateModuleRequest")
    void shouldReturnFailCreate(Integer id, ModuleRequests.CreateRequest request) {
        when(moduleRepository.findById(1)).thenReturn(Optional.of(new Module()));
        var user = new User();
        user.setUserId(1);
        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        var course = new Course();
        course.setCreator(user);
        when(courseRepository.findById(1)).thenReturn(Optional.of(course));
        var response = moduleService.create(id, request);
        System.out.println(response.message);
        assertFalse(response.success);
    }

}
