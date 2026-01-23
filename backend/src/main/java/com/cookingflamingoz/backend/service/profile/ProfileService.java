package com.cookingflamingoz.backend.service.profile;

import com.cookingflamingoz.backend.model.DifficultyLevel;
import com.cookingflamingoz.backend.model.User;
import com.cookingflamingoz.backend.model.EnrolleeProfile;
import com.cookingflamingoz.backend.model.UserTag;
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
    private final TagRepository tagRepository;

    public ProfileService(UserRepository userRepository, EnrolleeProfileRepository enrolleeProfileRepository, DifficultyLevelRepository difficultyLevelRepository, UserTagRepository userTagRepository, TagRepository tagRepository) {
        this.userRepository = userRepository;
        this.enrolleeProfileRepository = enrolleeProfileRepository;
        this.difficultyLevelRepository = difficultyLevelRepository;
        this.userTagRepository = userTagRepository;
        this.tagRepository = tagRepository;
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
        Set<UserTag> userTagSet = userTagRepository.findByUserId(user.getUserId());
        // Set<ProfileResults.UserTagInfo> userTagInfoSet = tagRepository.

        // ProfileResults.ProfileInfo profileInfo = new ProfileResults.ProfileInfo(user.getFirstname(), user.getSurname(), user.getEmail(), user.getCreatedAt(), user.getIsAdmin(), user.getIsModerator(), user.getIsVerified(), enrolleeProfile.getUsername(), difficultyLevel.getName());

        return new ProfileResults.GetAllResults(true, "Found all profile information."
                , null);
    }
    /*
    *     public AdminResults.InboxResult getInbox(int userId) {
    *    if (!validateAdmin(userId)) {
    *        return new AdminResults.InboxResult(false, "Access denied: Not an admin", null);
    *    }
    *    List<RequestSummary> requests = requestRepository.findAllProjectedBy();
    *    return new AdminResults.InboxResult(true, "Found " + requests.size() + " requests.", requests);
    * }
    * */
}
