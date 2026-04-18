import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { switchMap } from 'rxjs/operators';

import { JobService } from '../../core/services/job-requests.service';
import { EmailService } from '../../core/services/email.service';

import { JobRequest } from '../../core/models/job-request';
import { EmailReply } from '../../core/models/email-reply';

import { MatCardModule } from '@angular/material/card';
import { NgClass } from '@angular/common';
import { EmailResponseComponent } from './components/email-response/email-response.component';

@Component({
  standalone: true,
  templateUrl: './job-detail.component.html',
  styleUrls: ['./job-detail.component.css'],
  imports: [MatCardModule, NgClass, EmailResponseComponent],
})
export class JobDetailComponent implements OnInit, OnDestroy {
  job: JobRequest | null = null;
  emails: EmailReply[] = [];
  jobId: string | null = null;

  private sub?: Subscription;

  constructor(
    private route: ActivatedRoute,
    private jobService: JobService,
    private emailService: EmailService,
  ) {}

  ngOnInit(): void {
    this.sub = this.route.paramMap
      .pipe(
        switchMap((params) => {
          const id = params.get('id');
          this.jobId = id;

          if (!id) throw new Error('Missing job id');

          return this.jobService.getJobById(id);
        }),
      )
      .subscribe((job) => {
        this.job = job;

        if (job?.id) {
          this.emailService.getEmailsByJobId(job.id).subscribe((emails) => {
            this.emails = emails;
          });
        }
      });
  }

  ngOnDestroy(): void {
    this.sub?.unsubscribe();
  }
}
