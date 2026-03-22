import { ApplicationConfig, provideBrowserGlobalErrorListeners, provideZoneChangeDetection, provideZonelessChangeDetection } from '@angular/core';
import { LOCALE_ID } from '@angular/core';
import { registerLocaleData } from '@angular/common';
import {MAT_DATE_LOCALE} from "@angular/material/core";
import localePt from "@angular/common/locales/pt";
import { provideRouter } from '@angular/router';
import { provideEnvironmentNgxMask } from 'ngx-mask';
import { routes } from './app.routes';
import { provideClientHydration, withEventReplay } from '@angular/platform-browser';

registerLocaleData(localePt);
export const appConfig: ApplicationConfig = {
  providers: [
    provideEnvironmentNgxMask(),
    provideZonelessChangeDetection(),
    provideBrowserGlobalErrorListeners(),
    provideRouter(routes), 
    provideClientHydration(withEventReplay()),
    {provide: MAT_DATE_LOCALE, useValue:'pt-BR'},
    {provide: LOCALE_ID, useValue:'pt-BR'}
  ]
};
