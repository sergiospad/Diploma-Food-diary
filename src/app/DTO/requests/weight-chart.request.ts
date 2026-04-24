import {ChartPeriodType} from '../types';

export default interface WeightChartRequest{
  startDate: Date;
  endDate: Date;
  chartPeriodType: ChartPeriodType;
}
