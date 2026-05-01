import {Component, inject, input, OnInit, output} from '@angular/core';
import TagService from '../../../service/recipe_resource/tag.service';
import TagDto from '../../../DTO/entity_dto/recipe-recource/tag.dto';

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

}
