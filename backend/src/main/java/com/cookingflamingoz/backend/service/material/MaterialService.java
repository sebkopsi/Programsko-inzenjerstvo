package com.cookingflamingoz.backend.service.material;

import com.cookingflamingoz.backend.controller.material.MaterialRequests;
import com.cookingflamingoz.backend.model.Material;
import com.cookingflamingoz.backend.repository.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jdk.dynalink.StandardOperation;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@Service
public class MaterialService {
    @PersistenceContext
    private EntityManager entityManager;
    private final MaterialRepository materialRepository;
    private final WebClient fileServer;

    @Value(value = "${materials.folder}")
    private String MATERIALS_FOLDER;

    private static boolean shouldQueryFileServer(String type){
        return switch (type) {
            case "EXTERNAL" -> false;
            default -> true;
        };
    }

    public MaterialService(MaterialRepository materialRepository,@Qualifier("fileserverWebClient") WebClient fileServer){
        this.materialRepository = materialRepository;
        this.fileServer = fileServer;
    }

    public Resource getById(int id){
        Optional<Material> material =  materialRepository.findById(id);
        if(material.isEmpty()){
            throw new ResponseStatusException(NOT_FOUND, "Unable to find material data by id");

        }

        if(!shouldQueryFileServer(material.get().getType())){
            throw new ResponseStatusException(NO_CONTENT, "Material data should be fetched by info");
        }

        // query file server for content
        return fileServer.get().uri(material.get().getContents()).retrieve().onStatus(
                 HttpStatusCode::is4xxClientError, clientResponse ->{ throw new ResponseStatusException(NOT_FOUND, "Unable to find resource");}
        ).bodyToMono(Resource.class).block();
    }

    public MaterialResults.GetByIdResult getByIdInfo(int id){
        Optional<Material> material =  materialRepository.findById(id);
        return material.map(value -> new MaterialResults.GetByIdResult(true, "", value)).orElseGet(() -> new MaterialResults.GetByIdResult(false, "material not found", null));
    }




    public MaterialResults.CreateResult uploadFile(MaterialRequests.CreateRequest request){
        if(request.getName().isEmpty() || request.getFile().isEmpty() || request.getType().isEmpty()){
            return new MaterialResults.CreateResult(false, "request is empty", null);
        }

        if(MATERIALS_FOLDER.isEmpty()){
            throw new RuntimeException("MATERIAL_FOLDER IS NOT SET");
        }

        if(request.getFile().getContentType() == null){
            return new MaterialResults.CreateResult(false, "file does not have content type set", null);
        }

        UUID randomUuid = UUID.randomUUID();
        Path target = Paths.get(MATERIALS_FOLDER + "/material/" + randomUuid );
        try {
            Files.write(target, request.file.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            return new MaterialResults.CreateResult(false, "failed to create file: " + e.getMessage(), null);
        }


        Material newMaterial = new Material();
        newMaterial.setContents("/material/" + randomUuid);
        newMaterial.setType(request.getType());
        newMaterial.setName(request.getName());
        newMaterial = materialRepository.save(newMaterial);

        return new MaterialResults.CreateResult(true, "", newMaterial);
    }
}
