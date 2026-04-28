import {Component, inject, OnInit, output} from '@angular/core';
import {MatFormField, MatLabel} from '@angular/material/input';
import {MatOption, MatSelect} from '@angular/material/select';
import {FormsModule} from '@angular/forms';
import TagDto from '../../../DTO/entity_dto/recipe-recource/tag.dto';
import TagService from '../../../service/recipe_resource/tag.service';

@Component({
  selector: 'app-tag-show-section',
  imports: [
    MatFormField,
    MatLabel,
    MatSelect,
    FormsModule,
    MatOption
  ],
  templateUrl: './tag-show-section.html',
  styleUrl: './tag-show-section.css',
})
export class TagShowSection implements OnInit {
  private readonly tagService = inject(TagService);

  protected allTags?: TagDto[];
  protected selectedTags: TagDto[] = [];

  protected toMainComponentSelectedTags = output<TagDto[]>();

  ngOnInit(): void {
    this.tagService.getAllTags()
      .subscribe(data=>this.allTags = data);
  }

  onSelectedTagsChange(tags:TagDto[]){
    this.selectedTags = tags;
    this.toMainComponentSelectedTags.emit(tags);
  }
}
