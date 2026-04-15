package org.kane.domain.service.diary_record;

import lombok.RequiredArgsConstructor;
import org.kane.database.repository.diary_record.DiaryRecordRepository;
import org.kane.database.repository.meal.MealRepository;
import org.kane.database.repository.sport_activities.SportActivitiesRepository;
import org.kane.database.repository.user.UserRepository;
import org.kane.domain.DTO.entityDTO.daily_record.DiaryRecordShowDTO;
import org.kane.domain.DTO.entityDTO.meal.MealProjection;
import org.kane.domain.DTO.entityDTO.meal.MealShowDTO;
import org.kane.domain.DTO.entityDTO.meal_item.MealItemShowDTO;
import org.kane.domain.DTO.entityDTO.meal_item.TotalMealShowDTO;
import org.kane.domain.DTO.entityDTO.sport_activity.CalorieConsumptionShowDTO;
import org.kane.domain.DTO.request.DiaryRecordRequest;
import org.kane.domain.service.energy_value.EnergyValueService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Transactional
@Service
@RequiredArgsConstructor
public class DiaryRecordServiceImpl implements DiaryRecordService {

    private final DiaryRecordRepository diaryRecordRepository;
    private final UserRepository userRepository;
    private final MealRepository mealRepository;
    private final EnergyValueService energyValueService;
    private final SportActivitiesRepository sportActivitiesRepository;

    @Override
    public DiaryRecordShowDTO getDiaryRecord(Principal principal, DiaryRecordRequest diaryRecordRequest){
        var userID = userRepository.getCurrentUserId(principal);
        var map = diaryRecordRepository.getShowDTOMap(diaryRecordRequest.getRecordDate(), userID);
        List<MealShowDTO> meals = new ArrayList<>();
        for(Map.Entry<MealProjection, List<MealItemShowDTO>> mapEntry : map.entrySet())
            meals.add(getMealShowDTO(mapEntry));
        return DiaryRecordShowDTO.builder()
                .date(diaryRecordRequest.getRecordDate())
                .meals(meals)
                .total(getTotal(meals))

                .build();
    }

    private TotalMealShowDTO getTotal(List<MealShowDTO> meals){
        var total = new TotalMealShowDTO();
        for(MealShowDTO mealDTO : meals)
            total.add(mealDTO);
        return total;
    }

    private MealShowDTO getMealShowDTO(Map.Entry<MealProjection, List<MealItemShowDTO>> entry){
        MealShowDTO mealShowDTO =  new MealShowDTO();
        mealShowDTO.setId(entry.getKey().getId());
        mealShowDTO.setMealType(entry.getKey().getType());
        TotalMealShowDTO temp = new TotalMealShowDTO();
        var list = entry.getValue();
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
    public CalorieConsumptionShowDTO getConsumptionOfDiaryRecord(Long userID, LocalDate date){
        var BMR = energyValueService.getBMR(userID);
        var diaryInfo = diaryRecordRepository.getIDAndAutocalc(date, userID);
        var activities = sportActivitiesRepository.getActivities(diaryInfo.getId());
        return CalorieConsumptionShowDTO.builder()
                .inRestConsumption(BMR)
                .autoCalc(diaryInfo.getAutocalc())
                .sportActivityShowDTOList(activities)
                .build();
    }
}
