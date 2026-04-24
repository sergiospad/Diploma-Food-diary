import TagDTO from '../recipe-recource/tag.dto';
import IngredientShowDTO from '../recipe-recource/ingredient/ingredient-show.dto';
import CookingStageShowDTO from '../recipe-recource/cooking_stage/cooking-stage-show.dto';

export default interface RecipeShowDTO {
  id: number;
  authorName: string;
  avatarID:number;
  cookingTime: number;
  name: string;
  summary: string;
  illustrationID: number;
  tags: TagDTO[];
  ingredients: IngredientShowDTO[];
  cookingStages: CookingStageShowDTO[];
}
