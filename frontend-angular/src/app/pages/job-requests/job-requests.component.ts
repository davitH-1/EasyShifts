import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { JobCardComponent } from './components/job-card/job-card.component';
import { JobService } from '../../core/services/job-requests.service';
import { JobRequest } from '../../core/models/job-request';

@Component({
  standalone: true,
  imports: [CommonModule, JobCardComponent],
  templateUrl: './job-requests.component.html',
})
export class JobRequestsComponent {
  jobs: JobRequest[] = [];

  constructor(private jobService: JobService) {
    this.jobs = this.jobService.getJobs();
  }
}
