import {CaloricityType} from '../../types';


export default interface EnergyValueShowDTO {

  caloricityType: CaloricityType;
  productWeight: number;
  calories:number;
  protein: number;
  fat: number;
  carbs: number;
  proteinPer?:number;
  fatPer?:number;
  carbsPer?:number;
}
