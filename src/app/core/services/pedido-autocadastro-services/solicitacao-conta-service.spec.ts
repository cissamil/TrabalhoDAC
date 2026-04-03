import { TestBed } from '@angular/core/testing';

import { SolicitacaoContaService } from './solicitacao-conta-service';

describe('SolicitacaoContaService', () => {
  let service: SolicitacaoContaService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SolicitacaoContaService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
