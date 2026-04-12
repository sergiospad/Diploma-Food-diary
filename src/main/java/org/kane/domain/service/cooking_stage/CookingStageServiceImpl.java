package org.kane.domain.service.cooking_stage;

import lombok.RequiredArgsConstructor;
import org.kane.database.entity.recipe_recource.CookingStage;
import org.kane.database.repository.cooking_stage.CookingStageRepository;
import org.kane.database.repository.image_model.ImageModelRepository;
import org.kane.domain.DTO.entityDTO.cooking_stage.CookingStageCreateDTO;
import org.kane.domain.DTO.entityDTO.cooking_stage.CookingStageEditDescDTO;
import org.kane.domain.DTO.entityDTO.cooking_stage.CookingStageShowDTO;
import org.kane.exceptions.not_found.CookingStageNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CookingStageServiceImpl implements CookingStageService {
    private final ImageModelRepository imageModelRepository;
    private final CookingStageRepository cookingStageRepository;

    @Override
    public CookingStage createCookingStage(CookingStageCreateDTO cookingStageCreateDTO) {
        return CookingStage.builder()
                .stageNumber(cookingStageCreateDTO.getStageNumber())
                .description(cookingStageCreateDTO.getDescription())
                .image(imageModelRepository
                        .findById(cookingStageCreateDTO.getImageID())
                        .orElse(null))
                .build();
    }

    @Override
    public void editCookingStage(CookingStageEditDescDTO cookingStageEditDescDTO) {
        var cs = cookingStageRepository.findById(cookingStageEditDescDTO.getId())
                .orElseThrow(() -> new CookingStageNotFoundException("cooking stage not found"));
        cs.setDescription(cookingStageEditDescDTO.getDescription());
        cookingStageRepository.save(cs);
    }

    @Override
    public List<CookingStageShowDTO> getAllShowDTOByRecipeID(Long recipeID){
        return cookingStageRepository.findAllShowDTOByRecipeID(recipeID);
    }
}
