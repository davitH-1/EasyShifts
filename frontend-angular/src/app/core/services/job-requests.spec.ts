import { TestBed } from '@angular/core/testing';
import { JobRequestsComponent } from '../../pages/job-requests/job-requests.component';


describe('JobRequestsComponent', () => {
  let service: JobRequestsComponent;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(JobRequestsComponent);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
