import CookingStageEditDescDTO from '../recipe-recource/cooking_stage/cooking-stage-edit-desc.dto';
import IngredientEditDTO from '../recipe-recource/ingredient/ingredient-edit.dto';

export default interface RecipeEditDto{
  id:number;
  name:string;
  summary:string;
  cookingTime:number;

  addTags:number[];
  removeTags:number[];

  isPrivate:boolean;

  editedStages: CookingStageEditDescDTO[];
  editedIngredients: IngredientEditDTO[];
}
