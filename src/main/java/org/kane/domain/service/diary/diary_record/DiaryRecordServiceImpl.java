package org.kane.domain.service.diary.diary_record;

import lombok.RequiredArgsConstructor;
import org.kane.database.repository.diary.diary_record.DiaryRecordRepository;
import org.kane.database.repository.diary.meal.MealRepository;
import org.kane.database.repository.diary.sport_activities.SportActivitiesRepository;
import org.kane.database.repository.user.UserRepository;
import org.kane.domain.DTO.entityDTO.diary.daily_record.DiaryRecordShowDTO;
import org.kane.domain.DTO.entityDTO.diary.meal.MealProjection;
import org.kane.domain.DTO.entityDTO.diary.meal.MealShowDTO;
import org.kane.domain.DTO.entityDTO.diary.meal_item.MealItemShowDTO;
import org.kane.domain.DTO.entityDTO.diary.sport_activity.CalorieConsumptionShowDTO;
import org.kane.domain.DTO.request.DiaryRecordRequest;
import org.kane.domain.service.diary.meal.MealService;
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
    private final MealService mealService;

    @Override
    public DiaryRecordShowDTO getDiaryRecord(Principal principal, DiaryRecordRequest diaryRecordRequest){
        var userID = userRepository.getCurrentUserId(principal);
        var map = mealRepository.getShowDTOMap(diaryRecordRequest.getRecordDate(), userID);
        List<MealShowDTO> meals = new ArrayList<>();
        for(Map.Entry<MealProjection, List<MealItemShowDTO>> mapEntry : map.entrySet())
            meals.add(mealService.getMealShowDTO(mapEntry));
        return DiaryRecordShowDTO.builder()
                .date(diaryRecordRequest.getRecordDate())
                .meals(meals)
                .total(mealService.getTotal(meals))
                .calorieConsumption(getConsumptionOfDiaryRecord(userID, diaryRecordRequest.getRecordDate()))
                .build();
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
