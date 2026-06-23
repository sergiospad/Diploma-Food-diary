import SportActivityShowDTO from './sport-activity-show.dto';

export default interface CalorieConsumptionShowDTO{
  inRestConsumption: number;
  autoCalc: boolean;
  sportActivityShowDTOList: SportActivityShowDTO[];
}
