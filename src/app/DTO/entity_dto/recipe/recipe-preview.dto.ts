export default interface RecipePreviewDTO {
  id: number;
  name: string;
  summary: string;
  imageID:number;
  image?: string;
  isFavourite?: boolean;
}
