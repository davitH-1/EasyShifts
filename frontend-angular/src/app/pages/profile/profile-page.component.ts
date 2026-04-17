import { Component } from '@angular/core';
import { AsyncPipe } from '@angular/common';
import { MatCardModule } from '@angular/material/card';
import { AuthCardComponent } from './components/auth-card/auth-card.component';
import { UserService } from '../../core/services/user';
import { SignedInUserComponent } from './components/signed-in-user/signed-in-user.component';

@Component({
  selector: 'app-profile-page',
  imports: [MatCardModule, AuthCardComponent, SignedInUserComponent, AsyncPipe],
  templateUrl: './profile-page.component.html',
  styleUrl: './profile-page.component.css',
  standalone: true,
})
export class ProfilePageComponent {
  user$;

  constructor(private userService: UserService) {
    this.user$ = this.userService.user$;
  }
}
