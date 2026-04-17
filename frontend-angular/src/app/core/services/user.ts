import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { map, tap } from 'rxjs/operators';
import { AuthService, UserProfile } from './auth.service';

@Injectable({ providedIn: 'root' })
export class UserService {
  private currentUser = new BehaviorSubject<UserProfile | null>(null);
  readonly user$ = this.currentUser.asObservable();
  readonly isLoggedIn$ = this.user$.pipe(map(u => u !== null));

  constructor(private auth: AuthService) {}

  loadUser(): Observable<UserProfile | null> {
    return this.auth.getCurrentUser().pipe(tap(user => this.currentUser.next(user)));
  }

  clearUser(): void {
    this.currentUser.next(null);
  }
}