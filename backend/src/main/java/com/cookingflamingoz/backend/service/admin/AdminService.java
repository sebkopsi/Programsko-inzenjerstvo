package com.cookingflamingoz.backend.service.admin;

import com.cookingflamingoz.backend.model.Request;
import com.cookingflamingoz.backend.model.RequestSummary;
import com.cookingflamingoz.backend.model.User;
import com.cookingflamingoz.backend.repository.RequestRepository;
import com.cookingflamingoz.backend.repository.TagRepository;
import com.cookingflamingoz.backend.repository.UserRepository;
import com.cookingflamingoz.backend.service.profile.ProfileResults;
import com.cookingflamingoz.backend.service.admin.AdminResults;
import com.cookingflamingoz.backend.util.GenericResult;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Optional;

@Service
public class AdminService {
    private final RequestRepository requestRepository;
    private final UserRepository userRepository;
    private final TagRepository tagRepository;

    public AdminService(RequestRepository requestRepository, UserRepository userRepository, TagRepository tagRepository) {
        this.requestRepository = requestRepository;
        this.userRepository = userRepository;
        this.tagRepository = tagRepository;
    }

    public AdminResults.InboxResult getInbox(int userId) {
        if (!validateAdmin(userId)) {
            return new AdminResults.InboxResult(false, "Access denied: Not an admin", null);
        }
        List<RequestSummary> requests = requestRepository.findAllProjectedBy();
        User user = userRepository.findById(userId).isPresent() ? userRepository.findById(userId).get() : null;

        List<AdminResults.AdminSummaryInfo> adminSummaryInfoList = requests.stream()
                .map(ut -> { return new AdminResults.AdminSummaryInfo(
                            (ut.getSentByUserId() != null && userRepository.findById(ut.getSentByUserId()).isPresent() ?
                                    userRepository.findById(ut.getSentByUserId()).get().getEmail() : "Unknown"),
                            ut.getCreatedAt(),
                            ut.getSentByUserId(),
                            ut.getStatus(),
                            ut.getType(),
                            ut.getTitle(),
                            ut.getReqId()
                    );
                })
                .collect(java.util.stream.Collectors.toList());;
        return new AdminResults.InboxResult(true, "Found " + requests.size() + " requests.", adminSummaryInfoList);
    }

    public AdminResults.DetailsResult getById(int requestId, int userId) {
        if (!validateAdmin(userId)) {
            return new AdminResults.DetailsResult(false, "Access denied: Not an admin", null);
        }

        Optional<Request> request = requestRepository.findById(requestId);
        return request.map(value -> new AdminResults.DetailsResult(true, "", value))
                .orElseGet(() -> new AdminResults.DetailsResult(false, "Request not found", null));
    }

    public GenericResult updateStatus(int requestId, String newStatus, int userId) {
        if (!validateAdmin(userId)) {
            return new GenericResult(false, "Access denied: Not an admin");
        }

        Optional<Request> requestOptional = requestRepository.findById(requestId);
        if (requestOptional.isEmpty()) {
            return new GenericResult(false, "Request not found");
        }

        Request request = requestOptional.get();
        request.setStatus(newStatus);
        requestRepository.save(request);

        return new GenericResult(true, "Status successfully updated to: " + newStatus);
    }

    public AdminResults.StatisticResult statisticCalculation(int userId) {
        if (!validateAdmin(userId)) {
            return new AdminResults.StatisticResult(false, "Access denied: Not an admin",
                    -1, -1, -1,
                    null, null, null);
        }

        // Call the correct repository methods and handle possible nulls
        Long rawActive = userRepository.countByIsActiveTrue();
        Long rawTotal = userRepository.countAllUsers();
        Long rawVerified = userRepository.countVerifiedUsers();
        java.util.Map<String, Long> numUsersByDifficulty = userRepository.countUsersByDifficulty();

        int numActiveUsers = rawActive == null ? 0 : rawActive.intValue();
        int numTotalUsers = rawTotal == null ? 0 : rawTotal.intValue();
        int numVerifiedUsers = rawVerified == null ? 0 : rawVerified.intValue();
        if (numUsersByDifficulty == null) {
            numUsersByDifficulty = java.util.Map.of();
        }

        java.util.Map<String, Long> usersByTag = tagRepository.countUsersByTag();
        if (usersByTag == null) {
            usersByTag = java.util.Map.of();
        }

        java.util.Map<String, Long> coursesByTag = tagRepository.countCoursesByTag();
        if (coursesByTag == null) {
            usersByTag = java.util.Map.of();
        }

        return new AdminResults.StatisticResult(true, "All Good",
            Integer.valueOf(numActiveUsers), Integer.valueOf(numTotalUsers),
                Integer.valueOf(numVerifiedUsers),
                numUsersByDifficulty, usersByTag, coursesByTag);
    }

    private boolean validateAdmin(int userId) {
        return userRepository.findById(userId)
                .map(User::isAdmin)
                .orElse(false);
    }
}