import { Component } from '@angular/core';
import { AsyncPipe } from '@angular/common';
import { MatCardModule } from '@angular/material/card';
import { AuthCardComponent } from './components/auth-card/auth-card.component';
<<<<<<< HEAD
import { UserService } from '../../core/services/user';
=======
import { User } from '../../core/services/user';
>>>>>>> 60429bf0651a4cbfd38468d815c91bec405eea45

@Component({
  selector: 'app-profile-page',
  imports: [MatCardModule, AuthCardComponent, AsyncPipe],
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