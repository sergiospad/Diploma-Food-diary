
import RecipeEditDto from './recipe-edit.dto';
import CookingStageEditProjection from '../recipe-recource/cooking_stage/cooking-stage-edit-projection';

export default interface RecipeEditProjection extends RecipeEditDto{
  titleImage?: Blob;
  editedStages: CookingStageEditProjection[];
  removedStages: number[];
  removedIngredients: number[];
}
