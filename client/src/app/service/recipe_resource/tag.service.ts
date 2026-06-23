import {inject, Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Endpoint} from '../../util/endpoint';
import {Observable} from 'rxjs';
import TagDTO from '../../DTO/entity_dto/recipe-recource/tag.dto';

@Injectable({
  providedIn: 'root',
})
export default class TagService {
  private readonly http = inject(HttpClient);
  private readonly tagAPI = new Endpoint('tag');

  getAllTags():Observable<TagDTO[]>{
    return this.http.get<TagDTO[]>(
      this.tagAPI.builder()
        .points("all")
        .build())
  }
}
