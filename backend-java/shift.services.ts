import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({ providedIn: 'root' })
export class ShiftService {
    private apiUrl = 'http://localhost:8080/api/shifts';

    constructor(private http: HttpClient) {}

    getDetectedShifts() {
        return this.http.get<any[]>(`${this.apiUrl}/history`);
    }

    updateSettings(settings: any) {
        return this.http.post(`${this.apiUrl}/settings`, settings);
    }
}