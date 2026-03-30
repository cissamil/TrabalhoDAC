import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdmGerenciarClientes } from './adm-gerenciar-clientes';

describe('AdmGerenciarClientes', () => {
  let component: AdmGerenciarClientes;
  let fixture: ComponentFixture<AdmGerenciarClientes>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AdmGerenciarClientes]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AdmGerenciarClientes);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
