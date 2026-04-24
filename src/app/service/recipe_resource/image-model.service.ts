import {inject, Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Endpoint} from '../../util/endpoint';
import {ImageType} from '../../DTO/types';
import {Observable} from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class ImageModelService {

  private readonly http = inject(HttpClient);
  private readonly imageModelAPI = new Endpoint('imageModel');

  uploadImage(file: Blob, type: ImageType): Observable<number> {
    const formData = new FormData();
    formData.append('file', file);

    return this.http.put<number>(
      this.imageModelAPI.builder()
        .points('upload')
        .addParam('type', type)
        .build(),
      formData);
  }

  getImage(id: number): Observable<Blob> {
    return this.http.get(
      this.imageModelAPI.builder()
        .points('get', id.toString())
        .build(),
      {responseType: 'blob' as const})
  }

  updateImage(file: Blob, id: number): Observable<any> {
    const formData = new FormData();
    formData.append('file', file);

    return this.http.patch(
      this.imageModelAPI.builder()
        .points('update', id.toString())
        .build(),
      formData)
  }

  deleteImage(id:number):Observable<any>{
    return this.http.delete(
      this.imageModelAPI.builder()
      .points("delete", id.toString())
      .build())
  }
}
