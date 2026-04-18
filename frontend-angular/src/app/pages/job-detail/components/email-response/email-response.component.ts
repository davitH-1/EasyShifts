import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatDividerModule } from '@angular/material/divider';
import { EmailReply } from '../../../../core/models/email-reply';

@Component({
  selector: 'app-email-response',
  standalone: true,
  imports: [CommonModule, MatCardModule, MatButtonModule, MatIconModule, MatDividerModule],
  templateUrl: './email-response.component.html',
  styleUrls: ['./email-response.component.css'],
})
export class EmailResponseComponent implements OnInit {
  // We initialize this with a sample so the UI has something to show immediately
  sampleEmail!: EmailReply;

  ngOnInit(): void {
    this.sampleEmail = new EmailReply({
      id: 'msg-101',
      from: 'hiring-manager@company.com',
      to: 'candidate@email.com',
      subject: 'Interview Follow-up',
      bodyText:
        'Hi there,\n\nThank you for the interview yesterday. Please find the attached job description and let us know if you have any questions.\n\nBest regards,\nHR Team',
      receivedAt: new Date(), // Defaults to "now"
      direction: 'incoming',
      isRead: false,
      attachments: [
        {
          id: 'att-1',
          fileName: 'job-description.pdf',
          fileSize: 150000,
          contentType: 'application/pdf',
          url: '#',
        },
      ],
    });
  }
}
