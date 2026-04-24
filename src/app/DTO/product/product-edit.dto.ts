import BaseNutrition from '../nutritional_info/base-nurtition';

export default interface ProductEditDTO extends BaseNutrition{
  id: number
  title: string;
  authorId: number;
  description: string;
  categoryId: number;
  isPrivate: boolean;
}
