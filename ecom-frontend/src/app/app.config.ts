import { ApplicationConfig, provideZoneChangeDetection } from '@angular/core';
import {provideRouter, withComponentInputBinding} from '@angular/router';

import { routes } from './app.routes';
import { provideClientHydration } from '@angular/platform-browser';
import {provideHttpClient, withFetch, withInterceptors} from '@angular/common/http';
import {AbstractSecurityStorage, authInterceptor, provideAuth} from 'angular-auth-oidc-client';
import {environment} from '../environment/environment';
import {provideQueryClient, QueryClient} from '@tanstack/angular-query-experimental';
import {SsrStorageService} from './auth/ssr-storage.service';
import {provideNgxStripe} from 'ngx-stripe';

export const appConfig: ApplicationConfig = {
  providers: [
    provideClientHydration(),
    provideZoneChangeDetection({ eventCoalescing: true }),
    provideRouter(routes, withComponentInputBinding()),
    provideHttpClient(withFetch(), withInterceptors([authInterceptor()])),
    provideAuth({
      config: {
        authority: environment.kinde.authority,
        redirectUrl: environment.kinde.redirectUrl,
        postLogoutRedirectUri: environment.kinde.postLogoutRedirectUri,
        clientId: environment.kinde.clientId,
        scope: 'openid profile email offline',
        responseType: 'code',
        silentRenew: true,
        useRefreshToken: true,
        logLevel: 1,
        secureRoutes: [environment.apiUrl],
        customParamsAuthRequest: {
          audience: environment.kinde.audience,
        },
      },
    }),
    { provide: AbstractSecurityStorage, useClass: SsrStorageService },
    provideQueryClient(new QueryClient()),
    provideNgxStripe(environment.stripePublishableKey)
  ],
};
