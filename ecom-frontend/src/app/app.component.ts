import { Component, OnInit, inject } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import {
  FaConfig,
  FaIconComponent,
  FaIconLibrary,
} from '@fortawesome/angular-fontawesome';
import { fontAwesomeIcons } from './shared/font-awesome-icons';
import {NavbarComponent} from './layout/navbar/navbar.component';
import {FooterComponent} from './layout/footer/footer.component';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, FaIconComponent, NavbarComponent, FooterComponent],
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
