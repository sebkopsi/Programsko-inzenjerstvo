package com.cookingflamingoz.backend.service.profile;

import com.cookingflamingoz.backend.service.course.CourseResults;
import com.cookingflamingoz.backend.util.GenericResult;
import jakarta.annotation.Nullable;

import java.util.Set;
import java.util.Date;

public class ProfileResults {

    /*
     * 1# razina vještine (početnik/srednji/napredni), prehrambene preferencije i alergeni, omiljene kuhinje
     * 2# napredat po upisanim tečajevima
     * 3# povijest upisanih tecajeva
     * 4# biljeske
     * */
    public static class ProfileInfo {
        // Profile User Info
        public Integer userId;
        public String firstname;
        public String surname;
        public String email;
        public Date createdAt;
        public boolean isAdmin;
        public boolean isModerator;
        public boolean isVerified;

        // Profile Enrollee Info
        public String username;
        public String skillLevel;
        // Prehrambene preferencije
        public Set<UserTagInfo> tags;
        // Alergeni
        // Omiljene kuhinje

        // Napredak po upisanim tecajevima

        // Povijest upisanih tecajeva

        // Biljeske


        public ProfileInfo(Integer userId, String firstname, String surname, String email, Date createdAt, boolean isAdmin, boolean isModerator, boolean isVerified, String username, String skillLevel, Set<UserTagInfo> tags) {
            this.userId = userId;
            this.firstname = firstname;
            this.surname = surname;
            this.email = email;
            this.createdAt = createdAt;
            this.isAdmin = isAdmin;
            this.isModerator = isModerator;
            this.isVerified = isVerified;
            this.username = username;
            this.skillLevel = skillLevel;
            this.tags = tags;
        }
    }

    public static class UserTagInfo {
        public String name;
        public String category;
        public boolean preferred;

        UserTagInfo(String name, String category, boolean preferred){
            this.preferred = preferred;
            this.category = category;
            this.name = name;
        }
    }

    public static class GetAllResults extends GenericResult {
        public ProfileInfo data;

        public GetAllResults(boolean success, String message, @Nullable ProfileInfo data) {
            super(success, message);
            this.data = data;
        }

    }
}
