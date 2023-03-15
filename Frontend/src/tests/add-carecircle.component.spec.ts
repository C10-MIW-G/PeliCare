import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddCarecircleComponent } from '../app/components/add-carecircle/add-carecircle.component';

describe('AddCarecircleComponent', () => {
  let component: AddCarecircleComponent;
  let fixture: ComponentFixture<AddCarecircleComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AddCarecircleComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AddCarecircleComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
