import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CarecircleInfoWidgetComponent } from '../app/components/carecircle-info-widget/carecircle-info-widget.component';

describe('CarecircleInfoWidgetComponent', () => {
  let component: CarecircleInfoWidgetComponent;
  let fixture: ComponentFixture<CarecircleInfoWidgetComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CarecircleInfoWidgetComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CarecircleInfoWidgetComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
