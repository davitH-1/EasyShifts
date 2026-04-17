import { Routes } from '@angular/router';
import { HomeComponent } from './pages/home/home.component';
import { ProfilePageComponent } from './pages/profile/profile-page.component';
import { JobRequestsComponent } from './pages/job-requests/job-requests.component';
import { JobDetailComponent } from './pages/job-detail/job-detail.component';

export const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'jobs', component: JobRequestsComponent },
  { path: 'jobs/:id', component: JobDetailComponent },
  { path: 'profile', component: ProfilePageComponent },
];
