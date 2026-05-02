package org.kane.domain.service.recipe_recource.image_model;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.kane.database.entity.recipe_recource.ImageModel;
import org.kane.database.enum_types.ImageType;
import org.kane.database.repository.recipe_recource.image_model.ImageModelRepository;
import org.kane.database.repository.user.UserRepository;
import org.kane.exceptions.not_found.ImageNotFoundException;
import org.kane.util.ImageUploadService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;

import static org.kane.util.ImageUploadService.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ImageModelServiceImpl implements ImageModelService {
    private static final String ERROR_MES = "Image not found";
    private final UserRepository userRepository;
    private final ImageModelRepository imageModelRepository;

    @Transactional
    @Override
    public Long uploadImage(Principal principal, MultipartFile file, ImageType type) {
        var userId = userRepository.getCurrentUserId(principal);
        ImageModel imageModel = ImageModel.builder()
                .url(saveImage(file, userId, type.toString()))
                .imageType(type)
                .build();
        var img = imageModelRepository.save(imageModel);

        return img.getId();
    }

    @Override
    public byte[] getImage(Long id) {
        return imageModelRepository.findById(id)
                .map(ImageModel::getUrl)
                .flatMap(ImageUploadService::get)
                .orElseThrow(()-> new ImageNotFoundException(ERROR_MES));
    }

    @SneakyThrows
    @Transactional
    @Override
    public void updateImage(Principal principal, MultipartFile file, Long id) {
        var userId = userRepository.getCurrentUserId(principal);
        var imageModel = imageModelRepository.findById(id)
                .orElseThrow(()-> new ImageNotFoundException(ERROR_MES));
        delete(imageModel.getUrl().toString());
        imageModel.setUrl(saveImage(file, userId, imageModel.getImageType().toString()));
        imageModelRepository.save(imageModel);
    }
    @Transactional
    @Override
    public void deleteImage(Long id) {
        imageModelRepository.deleteById(id);
    }
}
