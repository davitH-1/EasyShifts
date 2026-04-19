import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';
import { JobCardComponent } from './components/job-card/job-card.component';
import { JobService } from '../../core/services/job-requests.service';
import { JobRequest } from '../../core/models/job-request';

@Component({
  standalone: true,
  imports: [CommonModule, JobCardComponent, MatButtonModule, MatIconModule, MatSnackBarModule],
  templateUrl: './job-requests.component.html',
})
export class JobRequestsComponent implements OnInit {
  jobs: JobRequest[] = [];
  syncing = false;

  constructor(private jobService: JobService, private snackBar: MatSnackBar) {}

  ngOnInit(): void {
    this.loadJobs();
  }

  loadJobs(): void {
    this.jobService.getJobs().subscribe({
      next: (jobs) => (this.jobs = jobs),
      error: () => (this.jobs = []),
    });
  }

  syncFromEmail(): void {
    this.syncing = true;
    this.jobService.syncFromEmail().subscribe({
      next: (res) => {
        this.snackBar.open(res.message, 'Dismiss', { duration: 4000 });
        this.loadJobs();
      },
      error: () => {
        this.snackBar.open('Sync failed. Make sure you are signed in.', 'Dismiss', { duration: 4000 });
      },
      complete: () => (this.syncing = false),
    });
  }
}