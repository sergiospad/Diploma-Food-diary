import TagDTO from '../recipe-recource/tag.dto';
import IngredientShowDTO from '../recipe-recource/ingredient/ingredient-show.dto';
import CookingStageShowDTO from '../recipe-recource/cooking_stage/cooking-stage-show.dto';
import EnergyValueShowDTO from '../nutritional_info/energy-value-show.dto';

export default interface RecipeShowDTO {
  id: number;
  authorName: string;
  avatarID:number;
  avatar?: string;
  cookingTime: number;
  name: string;
  summary: string;
  illustrationID: number;
  illustration?:string;
  tags: TagDTO[];
  ingredients: IngredientShowDTO[];
  energy: EnergyValueShowDTO;
  cookingStages: CookingStageShowDTO[];
}
