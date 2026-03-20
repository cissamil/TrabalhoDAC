import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TransferenciaCliente } from './transferencia-cliente';

describe('TransferenciaCliente', () => {
  let component: TransferenciaCliente;
  let fixture: ComponentFixture<TransferenciaCliente>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TransferenciaCliente]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TransferenciaCliente);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
