import IngredientCreateDTO from '../recipe-recource/ingredient/ingredient-create.dto';
import CookingStageCreateDTO from '../recipe-recource/cooking_stage/cooking-stage-create.dto';

export default interface RecipeCreateDTO{
  name: string;
  summary: string;
  illustrationID:number;
  isPrivate: boolean;
  tags: number[];
  cookingTime?: number;
  ingredients: IngredientCreateDTO[];
  stages: CookingStageCreateDTO[]
  illustration?: Blob;
}
