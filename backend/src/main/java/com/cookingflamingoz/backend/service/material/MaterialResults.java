package com.cookingflamingoz.backend.service.material;

import com.cookingflamingoz.backend.model.Material;
import com.cookingflamingoz.backend.util.GenericResult;
import jakarta.annotation.Nullable;

public class MaterialResults {
    public static class MaterialInfo {
        public Integer id;
        public String contents;
        public String type;
        public String name;
    }

    public static class GetByIdResult extends GenericResult {
        public MaterialInfo data = new MaterialInfo();

        public GetByIdResult(boolean success, String message,@Nullable Material data) {
            super(success, message);
            if(null != data) {
                this.data.contents = data.getContents();
                this.data.type = data.getType();
                this.data.id = data.getMaterialId();
                this.data.name = data.getName();
            }
        }
    }

    public static class CreateResult extends GenericResult {
        public MaterialInfo data = new MaterialInfo();
        public CreateResult(boolean success, String message,@Nullable  Material data) {
            super(success, message);
            if(null != data) {
                this.data.contents = data.getContents();
                this.data.type = data.getType();
                this.data.id = data.getMaterialId();
                this.data.name = data.getName();
            }
        }
    }
}
