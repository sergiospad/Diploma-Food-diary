import CookingStageEditDescDTO from './cooking-stage-edit-desc.dto';

export default interface CookingStageEditProjection extends CookingStageEditDescDTO{
  image?:Blob;
}
