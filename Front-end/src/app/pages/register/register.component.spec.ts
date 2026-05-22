import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ReactiveFormsModule } from '@angular/forms';
import { RegisterComponent } from './register.component';
import { provideHttpClient } from '@angular/common/http';
import { UserService } from '../../core/service/user.service';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';
import { expect, describe, it, beforeEach } from '@jest/globals';

describe('RegisterComponent', () => {
  let component: RegisterComponent;
  let fixture: ComponentFixture<RegisterComponent>;
  let mockUserService: any;

  beforeEach(async () => {
    jest.spyOn(window, 'alert').mockImplementation(() => {});

    mockUserService = {
      register: jest.fn().mockReturnValue(of({})),
    };

    await TestBed.configureTestingModule({
      imports: [RegisterComponent, ReactiveFormsModule, RouterTestingModule],
      providers: [
        provideHttpClient(),
        { provide: UserService, useValue: mockUserService },
      ],
    }).compileComponents();

    fixture = TestBed.createComponent(RegisterComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should not register if the form is invalid', () => {
    component.onSubmit();
    expect(mockUserService.register).not.toHaveBeenCalled();
    expect(component.submitted).toBe(true);
  });

  it('should register if the form is valid', () => {
    component.registerForm.setValue({
      firstName: 'Test',
      lastName: 'User',
      login: 'login',
      password: 'password',
    });

    component.onSubmit();

    expect(mockUserService.register).toHaveBeenCalled();
  });
});
