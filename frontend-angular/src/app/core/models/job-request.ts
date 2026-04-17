export interface JobRequest {
  id: string;
  locationName: string;
  dateTime: string;
  startTime: string;
  endTime: string;
  hours: number;
  status: 'pending' | 'confirmed' | 'rejected';
}
