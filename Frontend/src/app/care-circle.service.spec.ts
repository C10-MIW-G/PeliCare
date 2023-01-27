import { TestBed } from '@angular/core/testing';

import { CareCircleService } from './care-circle.service';

describe('CareCircleService', () => {
  let service: CareCircleService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CareCircleService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
