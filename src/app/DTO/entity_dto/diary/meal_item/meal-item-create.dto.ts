import {NutritionType} from '../../../types';

export default interface MealItemCreateDTO {
  nutritionID: number;
  nutritionType: NutritionType;
  /** `ProductWeight` на бэке; при `@JsonValue` — число в JSON. */
  weight: number;
}
