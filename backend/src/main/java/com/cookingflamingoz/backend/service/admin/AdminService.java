package com.cookingflamingoz.backend.service.admin;

import com.cookingflamingoz.backend.model.Request;
import com.cookingflamingoz.backend.model.RequestSummary;
import com.cookingflamingoz.backend.model.User;
import com.cookingflamingoz.backend.repository.RequestRepository;
import com.cookingflamingoz.backend.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
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
        return new AdminResults.InboxResult(true, "Found " + requests.size() + " requests.", requests);
    }

    public AdminResults.DetailsResult getById(int requestId, int userId) {
        if (!validateAdmin(userId)) {
            return new AdminResults.DetailsResult(false, "Access denied: Not an admin", null);
        }

        Optional<Request> request = requestRepository.findById(requestId);
        return request.map(value -> new AdminResults.DetailsResult(true, "", value))
                .orElseGet(() -> new AdminResults.DetailsResult(false, "Request not found", null));
    }

    private boolean validateAdmin(int userId) {
        return userRepository.findById(userId)
                .map(User::isAdmin)
                .orElse(false);
    }
}