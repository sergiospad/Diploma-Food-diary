import {inject, Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Endpoint} from '../../util/endpoint';
import SportsActivityCreateDTO from '../../DTO/entity_dto/diary/sport_activity/sports-activity-create.dto';
import {Observable} from 'rxjs';
import SportActivityShowDTO from '../../DTO/entity_dto/diary/sport_activity/sport-activity-show.dto';
import SportActivityEditDTO from '../../DTO/entity_dto/diary/sport_activity/sport-activity-edit.dto';

@Injectable({
  providedIn: 'root',
})
export default class SportsActivityService {
  private readonly http = inject(HttpClient);
  private readonly activityAPI = new Endpoint('sportsActivity');

  createSportActivity(activity: SportsActivityCreateDTO):Observable<SportActivityShowDTO>{
    return this.http.put<SportActivityShowDTO>(
      this.activityAPI.builder()
        .points("create")
        .build(),
      activity)
  }

  editActivity(activity: SportActivityEditDTO):Observable<SportActivityShowDTO>{
    return this.http.patch<SportActivityShowDTO>(
      this.activityAPI.builder()
        .points("edit")
        .build(),
      activity)
  }

  deleteActivity(id: number):Observable<any>{
    return this.http.delete(
      this.activityAPI.builder()
        .points("delete", id.toString())
        .build())
  }
}
