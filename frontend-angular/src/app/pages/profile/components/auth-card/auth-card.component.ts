import { Component } from '@angular/core';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { AuthService } from '../../../../core/services/auth.service';

@Component({
  standalone: true,
  selector: 'app-auth-card',
  imports: [MatCardModule, MatButtonModule],
  templateUrl: './auth-card.component.html',
})
export class AuthCardComponent {
  constructor(private auth: AuthService) {}

  login() {
    this.auth.loginWithGoogle();
  }
}
