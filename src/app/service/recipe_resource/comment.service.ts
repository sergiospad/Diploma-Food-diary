import {inject, Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Endpoint} from '../../util/endpoint';
import CommentCreateDTO from '../../DTO/entity_dto/recipe-recource/comment/comment-create.dto';
import {Observable} from 'rxjs';
import CommentShowDTO from '../../DTO/entity_dto/recipe-recource/comment/comment-show.dto';

@Injectable({
  providedIn: 'root',
})
export default class CommentService {
  private readonly http = inject(HttpClient);
  private readonly commentAPI = new Endpoint('comment');

  createComment(comment: CommentCreateDTO): Observable<CommentShowDTO> {
    return this.http.put<CommentShowDTO>(this.commentAPI.builder()
      .points("create")
      .build(),
      comment)
  }

  deleteComment(id: number): Observable<any> {
    return this.http.delete<any>(
      this.commentAPI.builder()
        .points("delete")
        .addParam("id", id.toString())
        .build())
  }

  getByRecipeID(id:number):Observable<CommentShowDTO[]>{
    const comms =this.http.get<CommentShowDTO[]>(
      this.commentAPI.builder()
        .points("get", id.toString())
      .build())
    comms.subscribe(data=>console.dir(data))
    return comms;
  }
}
