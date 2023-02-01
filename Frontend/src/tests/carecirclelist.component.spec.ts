import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CarecirclelistComponent } from '../app/carecirclelist/carecirclelist.component';

describe('CarecirclelistComponent', () => {
  let component: CarecirclelistComponent;
  let fixture: ComponentFixture<CarecirclelistComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CarecirclelistComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CarecirclelistComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
