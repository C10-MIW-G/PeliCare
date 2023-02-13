import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CarecircleUsersComponent } from '../app/carecircle-users/carecircle-users.component';

describe('CarecircleUsersComponent', () => {
  let component: CarecircleUsersComponent;
  let fixture: ComponentFixture<CarecircleUsersComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CarecircleUsersComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CarecircleUsersComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
