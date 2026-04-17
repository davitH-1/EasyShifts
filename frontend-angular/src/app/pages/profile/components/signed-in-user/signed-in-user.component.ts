import { Component, Input } from '@angular/core';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { AuthService } from '../../../../core/services/auth.service';
import { UserService } from '../../../../core/services/user';

@Component({
  selector: 'app-signed-in-user',
  standalone: true,
  imports: [MatCardModule, MatButtonModule],
  templateUrl: './signed-in-user.component.html',
  styleUrl: './signed-in-user.component.css',
})
export class SignedInUserComponent {
  @Input() user: any;
  isLoggedIn$;

  constructor(
    private userService: UserService,
    private auth: AuthService,
  ) {
    this.isLoggedIn$ = this.userService.isLoggedIn$;
  }

  logout() {
    console.log('LOGOUT CLICKED');

    this.auth.logout().subscribe({
      next: () => {
        console.log('logout success');

        this.userService.clearUser(); // IMPORTANT
      },
      error: (err) => {
        console.error('logout failed', err);
      },
    });
  }
}
