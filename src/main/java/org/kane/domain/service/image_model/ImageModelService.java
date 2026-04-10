package org.kane.domain.service.image_model;

import org.kane.database.enum_types.ImageType;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;

public interface ImageModelService {
    public Long uploadImage(Principal principal, MultipartFile file, ImageType type);
    public byte[] getImage(Long id);
    public void updateImage(Principal principal, MultipartFile file, Long id);
    public void deleteImage(Long id);
}
