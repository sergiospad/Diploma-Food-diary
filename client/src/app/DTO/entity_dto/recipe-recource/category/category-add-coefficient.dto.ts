import CoefficientShowDTO from '../coefficient/coefficient-show.dto';
import CoefficientCreateDTO from '../coefficient/coefficient-create.dto';

export default interface CategoryAddCoefficientDTO{
  id:number;
  coefficients:CoefficientCreateDTO[];
}
