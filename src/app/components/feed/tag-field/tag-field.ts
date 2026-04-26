import {Component, inject, OnInit} from '@angular/core';
import TagService from '../../../service/recipe_resource/tag.service';
import TagDto from '../../../DTO/entity_dto/recipe-recource/tag.dto';

@Component({
  selector: 'app-tag-field',
  standalone: true,
  imports: [],
  templateUrl: './tag-field.html',
  styleUrl: './tag-field.css',
})
export class TagField implements OnInit {
  private readonly tagService = inject(TagService);
  protected tags!: TagDto[];
  protected selectedTag: TagDto|undefined;

  ngOnInit(): void {
    this.tagService.getAllTags().subscribe(tags => this.tags = tags);
  }


}
