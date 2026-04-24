import BaseNutrition from '../nutritional_info/base-nurtition';

export default interface ProductCreateDTO extends BaseNutrition{
  title: string;
  description: string;
  categoryId: number;
  isPrivate: boolean;
}
