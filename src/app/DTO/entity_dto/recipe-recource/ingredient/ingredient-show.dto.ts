import MeasureUnitDTO from '../measure-unit.dto';

export default interface IngredientShowDTO {
  id:number;
  productName:string;
  amount:number;
  units: MeasureUnitDTO[];
}
