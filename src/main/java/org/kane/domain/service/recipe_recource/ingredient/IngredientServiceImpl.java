package org.kane.domain.service.recipe_recource.ingredient;

import lombok.RequiredArgsConstructor;
import org.kane.database.entity.Recipe;
import org.kane.database.entity.physical_quantity.ProductWeight;
import org.kane.database.entity.recipe_recource.Ingredient;
import org.kane.database.entity.recipe_recource.MeasureUnit;
import org.kane.database.repository.recipe_recource.coefficient.CoefficientRepository;
import org.kane.database.repository.recipe_recource.ingredient.IngredientRepository;
import org.kane.database.repository.recipe_recource.measure_unit.MeasureUnitRepository;
import org.kane.database.repository.product.ProductRepository;
import org.kane.domain.DTO.entityDTO.recipe_recource.ingredient.*;
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
    public Ingredient createIngredient(IngredientCreateDTO ingredientCreateDTO, Recipe recipe) {
        var product = productRepository.findById(ingredientCreateDTO.getProductID())
                .orElseThrow(()-> new NoSuchProductException("product not found"));
        var coeff = coefficientRepository.getCoefficientByProductID(ingredientCreateDTO.getProductID(), ingredientCreateDTO.getMeasureUnitID());
        ProductWeight pw = new ProductWeight(ingredientCreateDTO.getAmount()).multiply(coeff);
        MeasureUnit mu = measureUnitRepository.findById(ingredientCreateDTO.getMeasureUnitID())
                .orElseThrow(()->new MeasureUnitNotFound("measure unit not found"));
        return ingredientRepository.save(Ingredient.builder()
                .specMeasureUnit(mu)
                .weight(pw)
                .recipe(recipe)
                .product(product)
                .build());
    }

    @Transactional
    @Override
    public Ingredient updateIngredient(IngredientEditDTO ingredient) {
        var ing =  ingredientRepository.findById(ingredient.getId())
                .orElseThrow(()-> new IngredientNotFoundException("ingredient not found"));
        if(ingredient.getMeasureUnitID()!=null){
            var mu = measureUnitRepository.findById(ingredient.getMeasureUnitID())
                    .orElseThrow(()->new MeasureUnitNotFound("measure unit not found"));
            ing.setSpecMeasureUnit(mu);
        }
        if(ingredient.getProductID()!=null){
            var product = productRepository.findById(ingredient.getProductID())
                    .orElseThrow(()->new NoSuchProductException("product not found"));
            ing.setProduct(product);
        }
        if(ingredient.getAmount()!=null){
            ing.setWeight(recalculateWeight(ing, ingredient.getAmount()));
        }
        return ingredientRepository.save(ing);
    }

    private ProductWeight recalculateWeight(Ingredient ingredient, Double amount) {
        var coeff = coefficientRepository.getCoefficientByProductID(ingredient.getProduct().getId(),ingredient.getSpecMeasureUnit().getId());
        return new ProductWeight(amount).multiply(coeff);
    }


    @Transactional
    @Override
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
        var specMes = mes.stream()
                .filter(m->m.getId().equals(ingredientPreShowDTO.getMeasureUnitID()))
                .findFirst()
                .orElseThrow(()-> new MeasureUnitNotFound("measure unit not found"));
        mes.remove(specMes);
        mes.addFirst(specMes);
        return IngredientShowDTO.builder()
                .id(ingredientPreShowDTO.getId())
                .productName(ingredientPreShowDTO.getProductName())
                .amount(ingredientPreShowDTO.getAmount().divide(coeff).getValue())
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
