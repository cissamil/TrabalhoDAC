import { TestBed } from '@angular/core/testing';

import { GerenteService } from './gerente-services';

describe('GerenteService', () => {
  let service: GerenteService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(GerenteService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
