import WeightRecordShowDTO from './weight-record-show.dto';

export default interface CurrentWeightRecordShowDTO extends WeightRecordShowDTO{
  BMI: number;
}
