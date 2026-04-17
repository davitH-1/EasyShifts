import { Injectable } from '@angular/core';

// TODO: update with proper google oauth that gives gmail and calendar access
@Injectable({ providedIn: 'root' })
export class AuthService {
  loginWithGoogle() {
    window.location.href = 'http://localhost:8080/oauth2/authorization/google';
  }
}
