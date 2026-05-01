export default interface RecipePreviewDTO {
  id: number;
  name: string;
  summary: string;
  imageID: number;
  image?: string;
  /** Нормализуется в RecipeService; JSON от API может отдавать другое имя поля */
  isFavourite?: boolean;
  favorite?: boolean;
  favourite?: boolean;
}
