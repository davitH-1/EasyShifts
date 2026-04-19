import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JobRequest } from '../models/job-request';
import { environment } from '../../../environments/environment';

@Injectable({
  providedIn: 'root',
})
export class JobService {
  private readonly api = environment.apiBaseUrl;

  constructor(private http: HttpClient) {}

  getJobs(): Observable<JobRequest[]> {
    return this.http.get<JobRequest[]>(`${this.api}/api/jobs`, { withCredentials: true });
  }

  getJobById(id: string): Observable<JobRequest | null> {
    return this.http.get<JobRequest>(`${this.api}/api/jobs/${id}`, { withCredentials: true });
  }

  syncFromEmail(): Observable<{ synced: number; message: string }> {
    return this.http.post<{ synced: number; message: string }>(
      `${this.api}/api/gmail/sync`, {}, { withCredentials: true }
    );
  }
}