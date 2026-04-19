package org.kane.domain.service.recipe_recource.meal;

import lombok.RequiredArgsConstructor;
import org.kane.database.entity.diary.Meal;
import org.kane.database.enum_types.MealType;
import org.kane.database.repository.diary.diary_record.DiaryRecordRepository;
import org.kane.database.repository.diary.meal.MealRepository;
import org.kane.database.repository.user.UserRepository;
import org.kane.domain.DTO.entityDTO.diary.meal.MealCreateDTO;
import org.kane.domain.DTO.entityDTO.diary.meal.MealEditDTO;
import org.kane.domain.DTO.entityDTO.diary.meal.MealProjection;
import org.kane.domain.DTO.entityDTO.diary.meal.MealShowDTO;
import org.kane.domain.DTO.entityDTO.diary.meal_item.MealItemShowDTO;
import org.kane.domain.DTO.entityDTO.diary.meal_item.TotalMealShowDTO;
import org.kane.domain.mappers.MealEditMapper;
import org.kane.domain.service.recipe_recource.meal_item.MealItemService;
import org.kane.exceptions.not_found.MealNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MealServiceImpl implements MealService {
    private final MealRepository mealRepository;
    private final DiaryRecordRepository diaryRecordRepository;
    private final UserRepository userRepository;
    private final MealItemService mealItemService;
    private final MealEditMapper mealEditMapper;

    @Transactional
    @Override
    public MealShowDTO createMeal(Principal principal, MealCreateDTO mealCreateDTO){
        var userID = userRepository.getCurrentUserId(principal);
        var dailyRecord = diaryRecordRepository.getDiaryRecordByRecordDate(mealCreateDTO.getDailyRecordDate(), userID);
        Meal meal = Meal.builder()
                .mealTime(mealCreateDTO.getTime())
                .type(mealCreateDTO.getMealType())
                .dailyRecord(dailyRecord)
                .build();
        mealCreateDTO.getList().stream()
                .map(mealItemService::unpackMealItem)
                .forEach(meal::addMealItem);
        meal = mealRepository.save(meal);
        return getMealShowDTO(meal.getId(), meal.getType(), meal.getMealTime(), mealRepository.showMealItems(meal.getId()));
    }

    @Override
    public MealShowDTO getMealShowDTO(Map.Entry<MealProjection, List<MealItemShowDTO>> entry){
        return getMealShowDTO(entry.getKey().getId(),
                entry.getKey().getType(),
                entry.getKey().getTime(),
                entry.getValue());
    }

    private MealShowDTO getMealShowDTO(Long mealID, MealType type, LocalTime time, List<MealItemShowDTO> list){
        MealShowDTO mealShowDTO =  new MealShowDTO();
        mealShowDTO.setId(mealID);
        mealShowDTO.setMealType(type);
        mealShowDTO.setTime(time);
        TotalMealShowDTO temp = new TotalMealShowDTO();
        for (MealItemShowDTO mealItemShowDTO : list)
            temp.add(mealItemShowDTO);
        mealShowDTO.setCalories(temp.getCalories());
        mealShowDTO.setProteins(temp.getProteins());
        mealShowDTO.setFat(temp.getFat());
        mealShowDTO.setCarbs(temp.getCarbs());
        mealShowDTO.setShowDTOList(list);
        return mealShowDTO;
    }

    @Override
    public TotalMealShowDTO getTotal(List<MealShowDTO> meals){
        var total = new TotalMealShowDTO();
        for(MealShowDTO mealDTO : meals)
            total.add(mealDTO);
        return total;
    }

    @Override
    @Transactional
    public MealShowDTO editMeal(MealEditDTO mealEditDTO){
        var meal = mealRepository.findById(mealEditDTO.getMealID())
                .map(m-> mealEditMapper.copyMap(mealEditDTO, m))
                .map(mealRepository::save)
                .orElseThrow(()-> new MealNotFoundException("Meal not found"));
        mealEditDTO.getMealItemEditDTOList().forEach(mealItemService::updateMealItem);
        return getMealShowDTO(meal.getId(), meal.getType(), meal.getMealTime(), mealRepository.showMealItems(meal.getId()));
    }

}
