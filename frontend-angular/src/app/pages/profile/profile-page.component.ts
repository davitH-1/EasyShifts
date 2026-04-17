import { Component } from '@angular/core';
import { MatCardModule } from '@angular/material/card';
import { AuthCardComponent } from './components/auth-card/auth-card.component';

interface User {
  id: number;
  name: string;
  email: string;
  avatarUrl?: string;
}

@Component({
  selector: 'app-profile-page',
  imports: [MatCardModule, AuthCardComponent],
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
