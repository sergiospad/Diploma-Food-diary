import CoefficientCreateDTO from '../coefficient/coefficient-create.dto';

export default interface CategoryCreateDTO{
  name: string;
  coefficients: CoefficientCreateDTO[];
}
