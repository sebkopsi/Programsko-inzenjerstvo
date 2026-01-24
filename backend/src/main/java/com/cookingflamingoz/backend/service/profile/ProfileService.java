package com.cookingflamingoz.backend.service.profile;

import com.cookingflamingoz.backend.model.*;
import com.cookingflamingoz.backend.repository.*;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class ProfileService {
    private final UserRepository userRepository;
    private final EnrolleeProfileRepository enrolleeProfileRepository;
    private final DifficultyLevelRepository difficultyLevelRepository;
    private final UserTagRepository userTagRepository;
    private final EnrolledCourseRepository enrolledCourseRepository;

    public ProfileService(UserRepository userRepository, EnrolleeProfileRepository enrolleeProfileRepository, DifficultyLevelRepository difficultyLevelRepository, UserTagRepository userTagRepository, EnrolledCourseRepository enrolledCourseRepository) {
        this.userRepository = userRepository;
        this.enrolleeProfileRepository = enrolleeProfileRepository;
        this.difficultyLevelRepository = difficultyLevelRepository;
        this.userTagRepository = userTagRepository;
        this.enrolledCourseRepository = enrolledCourseRepository;
    }

    public ProfileResults.GetAllResults getAllProfileInfo(int userId){
        User user = userRepository.findById(userId).isPresent() ?
                userRepository.findById(userId).get()
                : null;

        if(user == null){
            return new ProfileResults.GetAllResults(false
                    , "User not found." , null);
        }

        EnrolleeProfile enrolleeProfile = enrolleeProfileRepository.getById(user.getEnrolleeId());
        DifficultyLevel difficultyLevel = difficultyLevelRepository.getById(enrolleeProfile.getEnrolleeId());
        Set<UserTag> userTagSet = userTagRepository.findAllByUserId(user.getUserId());
        Set<ProfileResults.UserTagInfo> userTagInfoSet = userTagSet.stream()
                .map(ut -> new ProfileResults.UserTagInfo(
                        ut.getTag().getName(),
                        ut.getTag().getCategory(),
                        ut.isPreferred()
                ))
                .collect(java.util.stream.Collectors.toSet());

        Set<EnrolledCourse> enrolledCourses = enrolledCourseRepository.findByUserId(user.getUserId());
        Set<ProfileResults.EnrolledCoursesInfo> enrolledCoursesInfoSet = enrolledCourses.stream()
                .map(ut -> new ProfileResults.EnrolledCoursesInfo(
                        ut.getCourse().getCourseId(),
                        ut.getUser().getUserId(),
                        ut.getCompletionPercentage(),
                        ut.getCertificateId(),
                        ut.getEnrolledAt(),
                        ut.getStatus(),
                        ut.getEndedAt()
                ))
                .collect(java.util.stream.Collectors.toSet());;

        ProfileResults.ProfileInfo data = new ProfileResults.ProfileInfo(user.getFirstname(), user.getSurname(), user.getEmail(), user.getCreatedAt(), user.getIsAdmin(), user.getIsModerator(), user.getIsVerified(), enrolleeProfile.getUsername(), difficultyLevel.getName(), userTagInfoSet, enrolledCoursesInfoSet);

        return new ProfileResults.GetAllResults(true, "Found all profile information."
                , data);
    }
}
