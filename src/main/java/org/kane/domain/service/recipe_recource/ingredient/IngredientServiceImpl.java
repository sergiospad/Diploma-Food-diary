package org.kane.domain.service.recipe_recource.ingredient;

import lombok.RequiredArgsConstructor;
import org.kane.database.entity.physical_quantity.ProductWeight;
import org.kane.database.entity.recipe_recource.Ingredient;
import org.kane.database.entity.recipe_recource.MeasureUnit;
import org.kane.database.repository.recipe_recource.coefficient.CoefficientRepository;
import org.kane.database.repository.recipe_recource.ingredient.IngredientRepository;
import org.kane.database.repository.recipe_recource.measure_unit.MeasureUnitRepository;
import org.kane.database.repository.product.ProductRepository;
import org.kane.domain.DTO.entityDTO.diary.recipe_recource.ingredient.*;
import org.kane.exceptions.not_found.IngredientNotFoundException;
import org.kane.exceptions.not_found.MeasureUnitNotFound;
import org.kane.exceptions.not_found.NoSuchProductException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class IngredientServiceImpl implements IngredientService{
    private final IngredientRepository ingredientRepository;
    private final ProductRepository productRepository;
    private final CoefficientRepository coefficientRepository;
    private final MeasureUnitRepository measureUnitRepository;

    @Override
    @Transactional
    public Ingredient createIngredient(IngredientCreateDTO ingredientCreateDTO) {
        var product = productRepository.findById(ingredientCreateDTO.getProductID())
                .orElseThrow(()-> new NoSuchProductException("product not found"));
        var coeff = coefficientRepository.getCoefficientByProductID(ingredientCreateDTO.getProductID(), ingredientCreateDTO.getMeasureUnitID());
        ProductWeight pw = new ProductWeight(ingredientCreateDTO.getAmount()).divide(coeff);
        MeasureUnit mu = measureUnitRepository.findById(ingredientCreateDTO.getMeasureUnitID())
                .orElseThrow(()->new MeasureUnitNotFound("measure unit not found"));
        return Ingredient.builder()
                .specMeasureUnit(mu)
                .weight(pw)
                .product(product)
                .build();
    }

    @Override
    public Ingredient updateIngredient(IngredientEditDTO ingredient) {
        var ing =  ingredientRepository.findById(ingredient.getId())
                .orElseThrow(()-> new IngredientNotFoundException("ingredient not found"));
        if(!ing.getSpecMeasureUnit().getId().equals(ingredient.getMeasureUnitID())){
            var mu = measureUnitRepository.findById(ingredient.getMeasureUnitID())
                    .orElseThrow(()->new MeasureUnitNotFound("measure unit not found"));
            ing.setSpecMeasureUnit(mu);
            recalculateWeight(ingredient, ing);
        } else if(Math.abs(ingredient.getAmount()-ing.getWeight().getValue())>0.0001){
            recalculateWeight(ingredient, ing);
        }
        if(!ing.getProduct().getId().equals(ingredient.getProductID())){
            var product = productRepository.findById(ingredient.getProductID())
                    .orElseThrow(()->new NoSuchProductException("product not found"));
            ing.setProduct(product);
        }
        return ingredientRepository.save(ing);
    }

    private void recalculateWeight(IngredientEditDTO ingredient, Ingredient ing) {
        var coeff = coefficientRepository.getCoefficientByProductID(ingredient.getProductID(), ingredient.getMeasureUnitID());
        ProductWeight pw = ProductWeight.calculateWeight(ingredient.getAmount(), coeff);
        ing.setWeight(pw);
    }

    public void removeIngredient(Long id){
        ingredientRepository.deleteById(id);
    }

    @Override
    public List<IngredientShowDTO> getShowIngredients(Long recipeID){
        return ingredientRepository.findPreShowDTO(recipeID).stream()
                .map(this::preShowToShowMap)
                .toList();
    }

    private IngredientShowDTO preShowToShowMap(IngredientPreShowProjection ingredientPreShowDTO){
        var coeff = coefficientRepository.getCoefficientByProductID(ingredientPreShowDTO.getProductID(), ingredientPreShowDTO.getMeasureUnitID());
        var mes = measureUnitRepository.findAllByIngredientID(ingredientPreShowDTO.getId());
        return IngredientShowDTO.builder()
                .id(ingredientPreShowDTO.getId())
                .productName(productRepository.findNameById(ingredientPreShowDTO.getProductID()))
                .amount(ingredientPreShowDTO.getAmount().calculateAmount(coeff))
                .units(mes)
                .build();
    }
    @Override
    public IngredientShowDTO toggleMeasureUnit(IngredientChangeDTO ingredientChangeDTO){
        var preShow = ingredientRepository.findPreShowDTOById(ingredientChangeDTO.getIngredientID());
        preShow.setMeasureUnitID(ingredientChangeDTO.getMeasureID());
        return preShowToShowMap(preShow);
    }

}
