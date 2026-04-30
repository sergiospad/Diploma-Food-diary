
import RecipeEditDto from './recipe-edit.dto';
import CookingStageEditProjection from '../recipe-recource/cooking_stage/cooking-stage-edit-projection';
import IngredientEditDTO from '../recipe-recource/ingredient/ingredient-edit.dto';

export default interface RecipeEditProjection extends RecipeEditDto{
  titleImage?: Blob;
  editedIngredients: IngredientEditDTO[];
  editedStages: CookingStageEditProjection[];
  removedStages: number[];
  removedIngredients: number[];
}
