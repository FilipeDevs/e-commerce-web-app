import { Component } from '@angular/core';
import {FeaturedComponent} from './featured/featured.component';

@Component({
  selector: 'app-home',
  imports: [
    FeaturedComponent
  ],
  templateUrl: './home.component.html',
  styleUrl: './home.component.scss'
})
export class HomeComponent {

}
