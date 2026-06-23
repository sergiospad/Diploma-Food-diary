import {Component} from '@angular/core';
import {RouterLink} from '@angular/router';
import {FEED_ROOT} from '../../util/roots';

@Component({
  selector: 'app-footer',
  standalone: true,
  imports: [RouterLink],
  templateUrl: './footer.html',
  styleUrl: './footer.css',
})
export class FooterComponent {
  protected readonly currentYear = new Date().getFullYear();
  protected readonly FEED_ROOT = FEED_ROOT;
}
