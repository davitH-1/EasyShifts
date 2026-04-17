import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { environment } from '../../../environments/environment';

export interface UserProfile {
  id?: number;
  name: string;
  email: string;
  avatarUrl?: string;
}

@Injectable({ providedIn: 'root' })
export class AuthService {
  private readonly api = environment.apiBaseUrl;

  constructor(private http: HttpClient) {}

  loginWithGoogle(): void {
    window.location.href = `${this.api}/oauth2/authorization/google`;
  }

  logout(): Observable<any> {
    return this.http.post(
      `${this.api}/logout`,
      {},
      {
        withCredentials: true,
        responseType: 'text' as 'json'
      }
    );
  }

  getCurrentUser(): Observable<UserProfile | null> {
    return this.http.get<UserProfile>(`${this.api}/api/user/me`, {
      withCredentials: true
    }).pipe(
      catchError(() => of(null)) // THIS is correct
    );
  }
}
