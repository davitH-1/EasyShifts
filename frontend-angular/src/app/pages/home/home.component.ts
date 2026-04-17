import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ShiftService } from '../../shiftAutomaticServices'; // Ensure this path is correct for your structure

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  // This array holds the shifts displayed in your table
  shifts: any[] = [];

  constructor(private shiftService: ShiftService) {}

  ngOnInit(): void {
    this.loadShifts();
  }

  /**
   * Fetches the shift history from the Spring Boot backend
   */
  loadShifts(): void {
    this.shiftService.getDetectedShifts().subscribe({
      next: (data) => {
        this.shifts = data;
        console.log('Successfully loaded shifts:', this.shifts);
      },
      error: (err) => {
        console.error('Failed to load shifts from backend:', err);
      }
    });
  }
}
