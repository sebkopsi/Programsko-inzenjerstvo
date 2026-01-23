package com.cookingflamingoz.backend.service.request;

import com.cookingflamingoz.backend.model.Request;
import com.cookingflamingoz.backend.repository.RequestRepository;
import com.cookingflamingoz.backend.util.GenericResult;
import org.springframework.stereotype.Service;

@Service
public class RequestService {
    private final RequestRepository requestRepository;

    public RequestService(RequestRepository requestRepository) {
        this.requestRepository = requestRepository;
    }

    // Method for users to submit reports
    public GenericResult submitReport(int senderId, String title, String content, String type, Integer courseId, Integer reportedUserId) {
        try {
            Request report = new Request();
            report.setSentByUserId(senderId);
            report.setTitle(title);
            report.setContent(content);
            report.setType(type); // npr. "REPORT_COMMENT" ili "REPORT_LESSON"
            report.setTargetCourseId(courseId);
            report.setReportedUserId(reportedUserId);
            report.setStatus("pending");

            requestRepository.save(report);
            return new GenericResult(true, "Prijava uspješno poslana administratorima.");
        } catch (Exception e) {
            return new GenericResult(false, "Greška pri slanju prijave: " + e.getMessage());
        }
    }
}

