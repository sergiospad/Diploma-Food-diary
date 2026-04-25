import {Injectable} from '@angular/core';

@Injectable({ providedIn: 'root' })
export class ImageUploadService{
  private readonly avatarCache: Map<number, string> = new Map();


  public convertBlobToDataUrl(blob: Blob): Promise<string> {
    return new Promise((resolve) => {
      const reader = new FileReader();

      reader.onloadend = () => {
        resolve(reader.result as string);
      };

      reader.readAsDataURL(blob);
    });
  }
}
