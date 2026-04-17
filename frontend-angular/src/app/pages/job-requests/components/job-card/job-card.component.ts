import { Component, Input } from '@angular/core';
import { MatCardModule } from '@angular/material/card';
import { JobRequest } from '../../../../core/models/job-request';
import { NgClass } from '@angular/common';
import { Router } from '@angular/router';

@Component({
  standalone: true,
  selector: 'app-job-card',
  imports: [MatCardModule, NgClass],
  templateUrl: './job-card.component.html',
  styleUrls: ['./job-card.component.css'],
})
export class JobCardComponent {
  @Input() job!: JobRequest;

  constructor(private router: Router) {}

  openJob() {
    this.router.navigate(['/jobs', this.job.id]);
  }
}
