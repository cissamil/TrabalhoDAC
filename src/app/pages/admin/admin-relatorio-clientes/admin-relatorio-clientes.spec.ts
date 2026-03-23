import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminRelatorioClientes } from './admin-relatorio-clientes';

describe('AdminRelatorioClientes', () => {
  let component: AdminRelatorioClientes;
  let fixture: ComponentFixture<AdminRelatorioClientes>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AdminRelatorioClientes]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AdminRelatorioClientes);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
