import { Routes } from '@angular/router';
import { HomeComponent } from './pages/home/home.component';
import { ProfilePageComponent } from './pages/profile/profile-page.component';

export const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'profile', component: ProfilePageComponent },
];
