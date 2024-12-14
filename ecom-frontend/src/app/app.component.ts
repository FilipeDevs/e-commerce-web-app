import { Component, OnInit, inject } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import {
  FaConfig,
  FaIconComponent,
  FaIconLibrary,
} from '@fortawesome/angular-fontawesome';
import { fontAwesomeIcons } from './shared/font-awesome-icons';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, FaIconComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss',
})
export class AppComponent implements OnInit {
  private faIconsLibrary = inject(FaIconLibrary);
  private faConfig = inject(FaConfig);

  ngOnInit(): void {
    this.initFontAwesome();
  }

  initFontAwesome() {
    this.faConfig.defaultPrefix = 'fas';
    this.faIconsLibrary.addIcons(...fontAwesomeIcons);
  }
}
