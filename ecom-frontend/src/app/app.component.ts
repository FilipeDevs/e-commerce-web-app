import {Component, OnInit, inject, PLATFORM_ID} from '@angular/core';
import { RouterOutlet } from '@angular/router';
import {
  FaConfig,
  FaIconLibrary,
} from '@fortawesome/angular-fontawesome';
import { fontAwesomeIcons } from './shared/font-awesome-icons';
import {NavbarComponent} from './layout/navbar/navbar.component';
import {FooterComponent} from './layout/footer/footer.component';
import {Oauth2Service} from './auth/oauth2.service';
import {isPlatformBrowser} from '@angular/common';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, NavbarComponent, FooterComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss',
})
export class AppComponent implements OnInit {
  private faIconsLibrary = inject(FaIconLibrary);
  private faConfig = inject(FaConfig);

  private oauth2Service = inject(Oauth2Service);

  platformId =  inject(PLATFORM_ID);

  constructor() {
    if(isPlatformBrowser(this.platformId)) {
      this.oauth2Service.initAuthentication();
    }
    this.oauth2Service.connectedUserQuery = this.oauth2Service.fetch();
  }


  ngOnInit(): void {
    this.initFontAwesome();
  }


  initFontAwesome() {
    this.faConfig.defaultPrefix = 'fas';
    this.faIconsLibrary.addIcons(...fontAwesomeIcons);
  }
}
