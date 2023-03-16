import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CircleimageComponent } from '../app/components/circleimage/circleimage.component';

describe('CircleimageComponent', () => {
  let component: CircleimageComponent;
  let fixture: ComponentFixture<CircleimageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CircleimageComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CircleimageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
