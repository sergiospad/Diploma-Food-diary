import {SortTypeRecipe} from '../types';

export default interface RecipePreviewRequest {
  sortType?: SortTypeRecipe;
  isFavoriteOnly?: boolean;
  tags?: number[];
  authorId?: number;
}
