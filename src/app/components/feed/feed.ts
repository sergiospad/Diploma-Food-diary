import { Component } from '@angular/core';
import {TagField} from './tag-field/tag-field';

@Component({
  selector: 'app-feed',
  standalone: true,
  imports: [
    TagField
  ],
  templateUrl: './feed.html',
  styleUrl: './feed.css',
})
export class FeedComponent {}
