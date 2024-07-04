import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CampaignCreateModalComponent } from './campaign-create-modal.component';

describe('CampaignCreateModalComponent', () => {
  let component: CampaignCreateModalComponent;
  let fixture: ComponentFixture<CampaignCreateModalComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CampaignCreateModalComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CampaignCreateModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
