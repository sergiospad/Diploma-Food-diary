
import BaseNutrition from './base-nurtition';
import {NutritionType} from '../../types';

export default interface NutritionShowProjection extends BaseNutrition{
  ID: number;
  type: NutritionType;
  name: string;
}
