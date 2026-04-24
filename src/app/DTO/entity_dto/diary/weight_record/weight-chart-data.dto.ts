import WeightPointDTO from './weight-point.dto';
import {ChartPeriodType} from '../../../types';

export default interface WeightChartDataDTO{
  weightList: WeightPointDTO[];
  period: ChartPeriodType;
  minWeight: number;
  maxWeight: number;
  avgWeight: number;
  weightChange: number;
  startDate: Date;
  endDate: Date;
}
