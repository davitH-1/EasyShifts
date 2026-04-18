import { Component, Input, OnInit } from '@angular/core';
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
export class EmailResponseComponent {
  @Input() email!: EmailReply;
}
