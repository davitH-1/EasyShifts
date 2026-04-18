import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EmailResponseComponent } from './email-response.component';

describe('EmailResponseComponent', () => {
  let component: EmailResponseComponent;
  let fixture: ComponentFixture<EmailResponseComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [EmailResponseComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(EmailResponseComponent);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
