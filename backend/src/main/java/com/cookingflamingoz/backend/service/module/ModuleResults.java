package com.cookingflamingoz.backend.service.module;

import com.cookingflamingoz.backend.model.Module;
import com.cookingflamingoz.backend.util.GenericResult;
import org.jetbrains.annotations.Nullable;

import java.util.Set;
import java.util.stream.Collectors;

public class ModuleResults {
    public static class ModuleInfo {
        public String name;

        public Integer id;

        ModuleInfo(String name, Integer id){
            this.name = name;
            this.id = id;
        }
    }

    public static class TagInfo {
        public String name;
        public String category;

        TagInfo(String name, String category){
            this.category = category;
            this.name = name;
        }
    }
    public static class GetByIdResult extends GenericResult {
        public ModuleInfo data;

        public GetByIdResult(boolean success, String message, Module module) {
            super(success, message);
            if(module != null ){
               this.data =  new ModuleInfo(
                        module.getName(),
                        module.getModuleId()
                );
            }
        }
    }

    public static class SearchResult extends GenericResult {
        public Set<ModuleInfo> data;
        public SearchResult(boolean success, String message, @Nullable Set<Module> data) {
            super(success, message);
            if (data != null) {
                this.data = data.stream().map(module -> {
                    return new ModuleInfo(
                        module.getName(),
                       module.getModuleId()
                );}).collect(Collectors.toSet());
            }

        }
    }

    public static class CreateResult extends GenericResult {
        public Integer id;

        public CreateResult(boolean success, String message, @Nullable Module module) {
            super(success, message);
            if(module != null){
                this.id = module.getModuleId();
            }
        }
    }

}
