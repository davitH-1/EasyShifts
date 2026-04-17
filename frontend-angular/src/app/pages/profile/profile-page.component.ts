import { Component } from '@angular/core';
import { MatCardModule } from '@angular/material/card';
import { AuthCardComponent } from './components/auth-card/auth-card.component';
import { User } from '../../core/services/user';

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
