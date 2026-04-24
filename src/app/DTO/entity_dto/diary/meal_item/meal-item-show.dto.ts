import BaseNutrition from '../../nutritional_info/base-nurtition';
import {NutritionType} from '../../../types';

export default interface MealItemShowDTO extends BaseNutrition {
  id: number;
  name: string;
  /** Если `ProductWeight` на бэке с `@JsonValue` как у `BaseNutrient` — в JSON число. Иначе опишите вложенный объект. */
  productWeight: number;
  nutritionType: NutritionType;
}
