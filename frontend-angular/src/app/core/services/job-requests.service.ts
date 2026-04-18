import { Injectable } from '@angular/core';
import { JobRequest } from '../models/job-request';
import { HttpClient } from '@angular/common/http';
import { delay, Observable, of } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class JobService {
  private mockJobs: JobRequest[] = [
    {
      id: '1',
      locationName: 'Warehouse A',
      dateTime: '2026-04-16 09:00',
      startTime: '12:00pm',
      endTime: '6:00pm',
      hours: 6,
      status: 'pending',
    },
    {
      id: '2',
      locationName: 'Logistics Hub',
      dateTime: '2026-04-17 12:00',
      startTime: '12:00pm',
      endTime: '4:00pm',
      hours: 4,
      status: 'confirmed',
    },
    {
      id: '3',
      locationName: 'Retail Store',
      dateTime: '2026-04-18 08:00',
      startTime: '12:00pm',
      endTime: '5:00pm',
      hours: 5,
      status: 'rejected',
    },
  ];

  constructor(private http: HttpClient) {}

  getJobs(): JobRequest[] {
    return this.mockJobs;
  }

  getJobById(id: string): Observable<JobRequest | null> {
    const job = this.mockJobs.find((j) => j.id === id);

    return of(job ?? null).pipe();
  }
  // getJobById(id: string): Observable<JobRequest> {
  //   // TODO: add the proper connection api call
  //   return this.http.get<JobRequest>(`/api/jobs/${id}`);
  // }
}
