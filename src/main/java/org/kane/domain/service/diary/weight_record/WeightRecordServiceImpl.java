package org.kane.domain.service.diary.weight_record;

import lombok.RequiredArgsConstructor;
import org.kane.database.entity.diary.WeightRecord;
import org.kane.database.entity.physical_quantity.HumanWeight;
import org.kane.database.repository.user.UserRepository;
import org.kane.database.repository.diary.weight_record.WeightRecordRepository;
import org.kane.domain.DTO.entityDTO.diary.weight_record.CurrentWeightRecordShowDTO;
import org.kane.domain.DTO.entityDTO.diary.weight_record.WeightRecordCreateDTO;
import org.kane.domain.DTO.entityDTO.diary.weight_record.WeightRecordShowDTO;
import org.kane.domain.DTO.entityDTO.diary.weight_record.for_chart.WeightChartDataDTO;
import org.kane.domain.DTO.entityDTO.diary.weight_record.for_chart.WeightPointDTO;
import org.kane.domain.DTO.request.WeightChartRequest;
import org.kane.domain.mappers.weight_chart.WeightChartRequestMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class WeightRecordServiceImpl implements WeightRecordService {

    private final WeightRecordRepository weightRecordRepository;
    private final UserRepository userRepository;
    private final WeightChartRequestMapper weightChartRequestMapper;

    @Override
    public List<WeightRecordShowDTO> getAllRecords(Principal principal){
        var user = userRepository.getCurrentUser(principal);
        return weightRecordRepository.findAllRecordsByUserID(user.getId());
    }

    @Override
    public CurrentWeightRecordShowDTO getCurrentRecord(Principal principal){
        var user = userRepository.getCurrentUser(principal);
        CurrentWeightRecordShowDTO rec = (CurrentWeightRecordShowDTO)weightRecordRepository.findLastRecordOfUser(user.getId());
        var height = user.getHeight();
        if(height==null){
            rec.setBMI(null);
        }
        else{
            rec.setBMI(rec.getMeasure().value()/(height*height * 0.0001));
        }
        return rec;
    }

    @Override
    public CurrentWeightRecordShowDTO createRecord(Principal principal, WeightRecordCreateDTO weightRecordCreateDTO){
        var user = userRepository.getCurrentUser(principal);
        var weightRecord = WeightRecord.builder()
                .measuredWeight(weightRecordCreateDTO.getMeasure())
                .dateOfMeasurement(weightRecordCreateDTO.getDateOfMeasurement())
                .user(user)
                .build();
        weightRecordRepository.save(weightRecord);
        return getCurrentRecord(principal);
    }

    @Override
    public WeightChartDataDTO formChart(Principal principal, WeightChartRequest weightChartRequest){
        var user = userRepository.getCurrentUser(principal);
        if(!weightRecordRepository.hasAnyData(user.getId(),weightChartRequest.getStartDate(), weightChartRequest.getEndDate())) return null;
        List<WeightPointDTO> list = switch (weightChartRequest.getChartPeriodType()){
            case DAY -> weightRecordRepository.getWeightByDay(user.getId(), weightChartRequest.getStartDate(), weightChartRequest.getEndDate());
            case WEEK -> weightRecordRepository.getWeightByWeek(user.getId(), weightChartRequest.getStartDate(), weightChartRequest.getEndDate());
            case MONTH -> weightRecordRepository.getWeightByMonth(user.getId(), weightChartRequest.getStartDate(), weightChartRequest.getEndDate());
            case YEAR -> weightRecordRepository.getWeightByYear(user.getId(), weightChartRequest.getStartDate(), weightChartRequest.getEndDate());
        };
        var dataChart = findMinMaxWeight(list);
        dataChart = weightChartRequestMapper.copyMap(weightChartRequest, dataChart);
        dataChart.setWeightList(list);
        var change = new HumanWeight(list.getFirst().getWeight().value() - list.getLast().getWeight().value());
        dataChart.setWeightChange(change);
        return dataChart;
    }

    private WeightChartDataDTO findMinMaxWeight(List<WeightPointDTO> list) {
        var minWeight = list.stream()
                .map(w -> w.getWeight().value())
                .min(Double::compareTo)
                .orElse(0.0);
        var maxWeight = list.stream()
                .map(w -> w.getWeight().value())
                .max(Double::compareTo)
                .orElse(0.0);
        double avgWeight = list.stream().map(w -> w.getWeight().value()).reduce(Double::sum).orElse(0.0);
        avgWeight = avgWeight / list.size();
        return WeightChartDataDTO.builder()
                .maxWeight(new HumanWeight(maxWeight))
                .minWeight(new HumanWeight(minWeight))
                .avgWeight(new HumanWeight(avgWeight))
                .build();
    }
}
