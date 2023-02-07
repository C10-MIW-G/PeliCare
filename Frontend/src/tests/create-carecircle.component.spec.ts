import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateCarecircleComponent } from '../app/create-carecircle/create-carecircle.component';

describe('CreateCarecircleComponent', () => {
  let component: CreateCarecircleComponent;
  let fixture: ComponentFixture<CreateCarecircleComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CreateCarecircleComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CreateCarecircleComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
