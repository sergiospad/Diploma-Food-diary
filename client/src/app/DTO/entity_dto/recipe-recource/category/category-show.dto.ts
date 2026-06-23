import CoefficientShowDTO from '../coefficient/coefficient-show.dto';

export default interface CategoryShowDto{
  id:number;
  name:string;
  coefficients: CoefficientShowDTO[];
}
