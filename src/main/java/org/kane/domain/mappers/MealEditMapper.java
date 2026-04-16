package org.kane.domain.mappers;

import org.kane.database.entity.diary.Meal;
import org.kane.domain.DTO.entityDTO.diary.meal.MealEditDTO;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class MealEditMapper implements CopyMapper<MealEditDTO, Meal>{
    @Override
    public Meal copyMap(MealEditDTO from, Meal to) {
        to.setMealTime(Optional.ofNullable(from.getTime()).orElse(to.getMealTime()));
        to.setType(Optional.ofNullable(from.getMealType()).orElse(to.getType()));
        return to;
    }
}
