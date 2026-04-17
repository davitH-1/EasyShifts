import { Injectable } from '@angular/core';
import { JobRequest } from '../models/job-request';

@Injectable({
  providedIn: 'root',
})
export class JobRequestsService {
  getJobs(): JobRequest[] {
    return [
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
  }
}
