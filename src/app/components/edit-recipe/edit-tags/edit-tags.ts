import {Component, inject, model, OnInit, signal} from '@angular/core';
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
  tags = signal<TagDto[]>([]);
  allTags :TagDto[] = [];

  onSelectedTagsChange(next: TagDto[]) {
    this.recipe.update((r) => ({...r, tags: next}));

    const prev = this.tags();
    const added = next
      .filter((t) => !prev.some((p) => p.id === t.id))
      .map((t) => t.id);
    const removed = prev
      .filter((p) => !next.some((t) => t.id === p.id))
      .map((t) => t.id);
    this.recipeEdit.update((rec) => ({
      ...rec,
      removeTags: [...(rec.removeTags ?? []), ...removed],
      addTags: [...(rec.addTags ?? []), ...added],
    }));
    this.tags.set(next);
  }

  ngOnInit(): void {
    this.tags.set([...(this.recipe().tags ?? [])]);
    this.tagService.getAllTags().subscribe((tags) => (this.allTags = tags));
  }
}
