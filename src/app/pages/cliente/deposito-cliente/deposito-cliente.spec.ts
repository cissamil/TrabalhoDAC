import { ComponentFixture, TestBed } from '@angular/core/testing';
import { DepositoCliente } from './deposito-cliente';

describe('DepositoCliente', () => {
  let component: DepositoCliente;
  let fixture: ComponentFixture<DepositoCliente>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DepositoCliente]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DepositoCliente);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
