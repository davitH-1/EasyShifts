import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { delay } from 'rxjs/operators';
import { EmailReply } from '../models/email-reply';

@Injectable({
  providedIn: 'root',
})
export class EmailService {
  private mockEmails: EmailReply[] = [
    new EmailReply({
      id: '1',
      conversationId: '1',
      subject: 'Initial Job Confirmation',
      bodyText: 'We have received your application for the job.',
      from: 'hr@company.com',
      to: 'you@email.com',
      receivedAt: new Date(),
      direction: 'incoming',
      isRead: true,
      attachments: [],
    }),
    new EmailReply({
      id: '2',
      conversationId: '1',
      subject: 'Interview Update',
      bodyText: 'Your interview is scheduled for tomorrow.',
      from: 'hr@company.com',
      to: 'you@email.com',
      receivedAt: new Date(),
      direction: 'incoming',
      isRead: false,
      attachments: [],
    }),
  ];

  getEmailsByJobId(jobId: string): Observable<EmailReply[]> {
    const emails = this.mockEmails.filter((e) => e.conversationId === jobId);

    return of(emails).pipe();
  }
}
