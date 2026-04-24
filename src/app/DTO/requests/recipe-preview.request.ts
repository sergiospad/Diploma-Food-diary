import {SortTypeRecipe} from '../types';

export default interface RecipePreviewRequest {
  sortType: SortTypeRecipe;
  favouriteOnly: boolean;
  tags: number[];
  authorId: number;
}
