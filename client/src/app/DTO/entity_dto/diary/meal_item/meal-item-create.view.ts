import MealItemCreateDTO from './meal-item-create.dto';
import BaseNutrition from '../../nutritional_info/base-nurtition';

export default interface MealItemCreateView extends MealItemCreateDTO, BaseNutrition{
  nutritionName: string;
}
