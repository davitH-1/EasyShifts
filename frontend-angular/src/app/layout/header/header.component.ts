import { Component } from '@angular/core';
import { AsyncPipe } from '@angular/common';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatButtonModule } from '@angular/material/button';
import { MatDividerModule } from '@angular/material/divider';
import { RouterLink } from '@angular/router';
import { AuthService } from '../../core/services/auth.service';
import { UserService } from '../../core/services/user';

@Component({
  selector: 'app-header',
  standalone: true,
  imports: [MatToolbarModule, MatButtonModule, MatDividerModule, RouterLink, AsyncPipe],
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css'],
})
export class HeaderComponent {
  isLoggedIn$;

  constructor(
    private userService: UserService,
    private auth: AuthService,
  ) {
    this.isLoggedIn$ = this.userService.isLoggedIn$;
  }

  logout(): void {
    this.auth.logout().subscribe({
      complete: () => {
        this.userService.clearUser();
        window.location.href = '/';
      },
    });
  }
}