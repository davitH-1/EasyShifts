import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EmailChainCardComponent } from './email-chain-card.component';

describe('EmailChainCardComponent', () => {
  let component: EmailChainCardComponent;
  let fixture: ComponentFixture<EmailChainCardComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [EmailChainCardComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(EmailChainCardComponent);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
