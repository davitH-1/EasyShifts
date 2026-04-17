import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { JobCardComponent } from './components/job-card/job-card.component';
import { JobRequestsService } from '../../core/services/job-requests.service';
import { JobRequest } from '../../core/models/job-request';

@Component({
  standalone: true,
  imports: [CommonModule, JobCardComponent],
  templateUrl: './job-requests.component.html',
})
export class JobRequestsComponent {
  jobs: JobRequest[] = [];

  constructor(private jobService: JobRequestsService) {
    this.jobs = this.jobService.getJobs();
  }
}
