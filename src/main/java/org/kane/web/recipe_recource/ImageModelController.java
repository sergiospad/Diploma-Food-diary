package org.kane.web.recipe_recource;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kane.database.enum_types.ImageType;
import org.kane.domain.DTO.response.MessageResponse;
import org.kane.domain.service.recipe_recource.image_model.ImageModelService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/api/imageModel")
@RequiredArgsConstructor
public class ImageModelController {

    private final ImageModelService imageModelService;

    /**
     * PUT /api/imageModel/upload?type=RECIPE
     */
    @PutMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<Long> uploadImage(Principal principal,
                                            @RequestParam ImageType type,
                                            @RequestPart("file") MultipartFile file) {
        var result = imageModelService.uploadImage(principal, file, type);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/get/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<byte[]> getImage(@PathVariable Long id) {
        var result = imageModelService.getImage(id);
        return ResponseEntity.ok(result);
    }

    @PatchMapping(value ="/update/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<MessageResponse> updateImage(Principal principal,
                                                       @PathVariable Long id,
                                                       @RequestPart("file") MultipartFile file){
        imageModelService.updateImage(principal, file, id);
        return ResponseEntity.ok(new MessageResponse("Image has been updated successfully"));
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<MessageResponse> deleteImage(@PathVariable Long id){
        imageModelService.deleteImage(id);
        return ResponseEntity.ok(new MessageResponse("Image has been deleted successfully"));
    }
}
