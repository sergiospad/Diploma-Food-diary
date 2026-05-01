import {Component, inject, input, OnInit, output} from '@angular/core';
import TagService from '../../../service/recipe_resource/tag.service';
import TagDto from '../../../DTO/entity_dto/recipe-recource/tag.dto';
import {FEED_ROOT} from '../../../util/roots';
import {Router} from '@angular/router';

@Component({
  selector: 'app-tag-field',
  standalone: true,
  imports: [],
  templateUrl: './tag-field.html',
  styleUrl: './tag-field.css',
})
export class TagField  {
  tags = input.required<TagDto[]>();
  protected selectedTag= output<TagDto>();
  router = inject(Router);

}
