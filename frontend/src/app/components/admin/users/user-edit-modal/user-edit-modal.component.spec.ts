import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UserEditModalComponent } from './user-edit-modal.component';

describe('UserEditModalComponent', () => {
  let component: UserEditModalComponent;
  let fixture: ComponentFixture<UserEditModalComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [UserEditModalComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(UserEditModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
