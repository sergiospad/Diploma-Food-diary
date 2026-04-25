export default interface CommentShowDTO{
  id:number;
  username:string;
  userAvatarID:number;
  message:string;
  createdAt:Date;
  imageID:number;

  image?: Blob;
  userAvatar?: Blob;
}
