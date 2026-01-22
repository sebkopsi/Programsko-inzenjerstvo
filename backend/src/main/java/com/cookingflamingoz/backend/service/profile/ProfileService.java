package com.cookingflamingoz.backend.service.profile;

import com.cookingflamingoz.backend.model.User;
import com.cookingflamingoz.backend.model.EnrolleeProfile;
import com.cookingflamingoz.backend.repository.UserRepository;
import com.cookingflamingoz.backend.repository.EnrolleeProfileRepository;
import org.springframework.stereotype.Service;

@Service
public class ProfileService {
    private final UserRepository userRepository;
    private final EnrolleeProfileRepository enrolleeProfileRepository;

    public ProfileService(UserRepository userRepository, EnrolleeProfileRepository enrolleeProfileRepository) {
        this.userRepository = userRepository;
        this.enrolleeProfileRepository = enrolleeProfileRepository;
    }
}
