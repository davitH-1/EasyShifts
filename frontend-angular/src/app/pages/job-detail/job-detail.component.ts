import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

@Component({
  standalone: true,
  templateUrl: './job-detail.component.html',
})
export class JobDetailComponent {
  jobId: string | null = null;

  constructor(private route: ActivatedRoute) {
    this.jobId = this.route.snapshot.paramMap.get('id');
  }
}
