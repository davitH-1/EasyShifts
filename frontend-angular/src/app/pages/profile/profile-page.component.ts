import { Component } from '@angular/core';
import { MatCardModule } from '@angular/material/card';
import { NgIf } from '@angular/common';

interface User {
  id: number;
  name: string;
  email: string;
  avatarUrl?: string;
}

@Component({
  selector: 'app-profile-page',
  imports: [MatCardModule],
  templateUrl: './profile-page.component.html',
  styleUrl: './profile-page.component.css',
  standalone: true,
})
export class ProfilePageComponent {
  user: any = null;
  loading = true;

  ngOnInit() {
    this.user = {
      name: 'Test User',
      email: 'test@example.com',
    };

    this.loading = false;
  }
}
