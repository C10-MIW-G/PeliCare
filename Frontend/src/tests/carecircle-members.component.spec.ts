import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CarecircleMembersComponent } from '../app/carecircle-members/carecircle-members.component';

describe('CarecircleMembersComponent', () => {
  let component: CarecircleMembersComponent;
  let fixture: ComponentFixture<CarecircleMembersComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CarecircleMembersComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CarecircleMembersComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
