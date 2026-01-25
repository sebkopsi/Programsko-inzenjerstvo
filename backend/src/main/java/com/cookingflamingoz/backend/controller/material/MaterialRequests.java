package com.cookingflamingoz.backend.controller.material;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@AllArgsConstructor
public class MaterialRequests {

    @Getter
    @Setter
    @AllArgsConstructor
    public static class CreateRequest {
        public String type;
        public String name;
        public MultipartFile file;
    }
}
