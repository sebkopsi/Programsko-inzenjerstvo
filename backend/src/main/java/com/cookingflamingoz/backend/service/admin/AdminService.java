package com.cookingflamingoz.backend.service.admin;

import com.cookingflamingoz.backend.model.Request;
import com.cookingflamingoz.backend.model.RequestSummary;
import com.cookingflamingoz.backend.model.User;
import com.cookingflamingoz.backend.repository.RequestRepository;
import com.cookingflamingoz.backend.repository.UserRepository;
import com.cookingflamingoz.backend.service.profile.ProfileResults;
import com.cookingflamingoz.backend.util.GenericResult;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.Optional;

@Service
public class AdminService {
    private final RequestRepository requestRepository;
    private final UserRepository userRepository;

    public AdminService(RequestRepository requestRepository, UserRepository userRepository) {
        this.requestRepository = requestRepository;
        this.userRepository = userRepository;
    }

    public AdminResults.InboxResult getInbox(int userId) {
        if (!validateAdmin(userId)) {
            return new AdminResults.InboxResult(false, "Access denied: Not an admin", null);
        }
        List<RequestSummary> requests = requestRepository.findAllProjectedBy();
        User user = userRepository.findById(userId).isPresent() ? userRepository.findById(userId).get() : null;

        List<AdminResults.AdminSummaryInfo> adminSummaryInfoList = requests.stream()
                .map(ut -> { return new AdminResults.AdminSummaryInfo(
                            user.getEmail(),
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


    private boolean validateAdmin(int userId) {
        return userRepository.findById(userId)
                .map(User::isAdmin)
                .orElse(false);
    }
}