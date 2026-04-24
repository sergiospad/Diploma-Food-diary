import {NutritionType} from '../types';
import BaseNutrition from './base-nurtition';

export default interface NutritionShowProjection extends BaseNutrition{
  ID: number;
  type: NutritionType;
  name: string;
}
