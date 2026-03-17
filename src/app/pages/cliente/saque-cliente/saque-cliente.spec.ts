import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SaqueCliente } from './saque-cliente';

describe('SaqueCliente', () => {
  let component: SaqueCliente;
  let fixture: ComponentFixture<SaqueCliente>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SaqueCliente]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SaqueCliente);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
