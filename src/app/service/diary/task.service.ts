import {inject, Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Endpoint} from '../../util/endpoint';
import TaskCreateDTO from '../../DTO/entity_dto/diary/task/task-create.dto';
import {Observable} from 'rxjs';
import CurrentTaskShowDTO from '../../DTO/entity_dto/diary/task/current-task-show.dto';
import ChangeStatusTaskRequest from '../../DTO/requests/change-status-task.request';
import TaskArchiveShowDTO from '../../DTO/entity_dto/diary/task/task-archive-show.dto';

@Injectable({
  providedIn: 'root',
})
export class TaskService {
  private readonly http = inject(HttpClient);
  private readonly taskAPI = new Endpoint('task');

  createTask(task: TaskCreateDTO):Observable<CurrentTaskShowDTO>{
    return this.http.put<CurrentTaskShowDTO>(
      this.taskAPI.builder()
        .points("create")
        .build(),
      task);
  }

  getCurrentTask():Observable<CurrentTaskShowDTO>{
    return this.http.get<CurrentTaskShowDTO>(
      this.taskAPI.builder()
        .points("current")
        .build());
  }

  changeStatus(task: ChangeStatusTaskRequest):Observable<any>{
    return this.http.patch(
      this.taskAPI.builder()
        .points("changeStatus")
        .build(),
      task);
  }

  getArchiveTasks():Observable<TaskArchiveShowDTO[]>{
    return this.http.get<TaskArchiveShowDTO[]>(
      this.taskAPI.builder()
        .points("archive")
        .build());
  }
}
