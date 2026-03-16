import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ClienteMainPage } from './cliente-main-page';

describe('ClienteMainPage', () => {
  let component: ClienteMainPage;
  let fixture: ComponentFixture<ClienteMainPage>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ClienteMainPage]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ClienteMainPage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
