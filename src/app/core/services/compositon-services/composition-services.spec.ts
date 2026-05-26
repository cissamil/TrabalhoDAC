import { TestBed } from '@angular/core/testing';

import { CompositionServices } from './composition-services';

describe('CompositionServices', () => {
  let service: CompositionServices;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CompositionServices);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
