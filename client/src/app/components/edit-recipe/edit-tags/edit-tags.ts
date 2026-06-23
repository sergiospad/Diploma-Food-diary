import {Component, inject, model, OnInit} from '@angular/core';
import {MatFormField, MatLabel} from '@angular/material/input';
import {MatOption} from '@angular/material/core';
import {MatSelect} from '@angular/material/select';
import {FormsModule} from '@angular/forms';
import RecipeShowDTO from '../../../DTO/entity_dto/recipe/recipe-show.dto';
import RecipeEditProjection from '../../../DTO/entity_dto/recipe/recipe-edit-projection';
import TagDto from '../../../DTO/entity_dto/recipe-recource/tag.dto';
import TagService from '../../../service/recipe_resource/tag.service';

@Component({
  selector: 'app-edit-tags',
  imports: [
    MatFormField,
    MatLabel,
    MatOption,
    MatSelect,
    FormsModule
  ],
  templateUrl: './edit-tags.html',
  styleUrl: './edit-tags.css',
})
export class EditTags implements OnInit {
  recipe = model.required<RecipeShowDTO>();
  tagService = inject(TagService);
  recipeEdit = model.required<RecipeEditProjection>();
  allTags :TagDto[] = [];
  protected readonly compareTags = (a: TagDto | null, b: TagDto | null): boolean =>
    a?.id === b?.id;

  onSelectedTagsChange(next: TagDto[]) {
    const normalizedNext = [...next];
    const prev = [...(this.recipe().tags ?? [])];
    this.recipe.update((r) => ({...r, tags: normalizedNext}));

    const added = normalizedNext
      .filter((t) => !prev.some((p) => p.id === t.id))
      .map((t) => t.id);
    const removed = prev
      .filter((p) => !normalizedNext.some((t) => t.id === p.id))
      .map((t) => t.id);
    this.recipeEdit.update((rec) => ({
      ...rec,
      removeTags: [...(rec.removeTags ?? []), ...removed],
      addTags: [...(rec.addTags ?? []), ...added],
    }));
  }

  ngOnInit(): void {
    this.tagService.getAllTags().subscribe((tags) => (this.allTags = tags));
  }
}
