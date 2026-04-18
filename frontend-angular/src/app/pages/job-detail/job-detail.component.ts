import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { EmailResponseComponent } from './components/email-response/email-response.component';

@Component({
  standalone: true,
  templateUrl: './job-detail.component.html',
  imports: [EmailResponseComponent],
})
export class JobDetailComponent {
  jobId: string | null = null;

  constructor(private route: ActivatedRoute) {
    this.jobId = this.route.snapshot.paramMap.get('id');
  }
}
