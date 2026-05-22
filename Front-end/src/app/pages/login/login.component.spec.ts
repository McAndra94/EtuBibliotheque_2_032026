import { ComponentFixture, TestBed } from '@angular/core/testing';
import { LoginComponent } from './login.component';
import { AuthService } from './../../services/auth.service';
import { Router } from '@angular/router';
import { ReactiveFormsModule } from '@angular/forms';
import { of, throwError } from 'rxjs';

describe('LoginComponent', () => {
  let component: LoginComponent;
  let fixture: ComponentFixture<LoginComponent>;
  let authServiceSpy: any;
  let routerSpy: any;

  beforeEach(async () => {
    authServiceSpy = { login: jest.fn() };
    routerSpy = { navigate: jest.fn() };

    Object.defineProperty(window, 'localStorage', {
      value: { setItem: jest.fn() },
      writable: true,
    });

    await TestBed.configureTestingModule({
      imports: [LoginComponent, ReactiveFormsModule],
      providers: [
        { provide: AuthService, useValue: authServiceSpy },
        { provide: Router, useValue: routerSpy },
      ],
    }).compileComponents();

    fixture = TestBed.createComponent(LoginComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should not call authService if form is invalid', () => {
    component.loginForm.setValue({ login: '', password: '' });
    component.onSubmit();
    expect(authServiceSpy.login).not.toHaveBeenCalled();
  });

  it('should handle successful login', () => {
    authServiceSpy.login.mockReturnValue(of('fake-token'));

    component.loginForm.setValue({ login: 'admin', password: 'password' });
    component.onSubmit();

    expect(authServiceSpy.login).toHaveBeenCalledWith('admin', 'password');
    expect(localStorage.setItem).toHaveBeenCalledWith('token', 'fake-token');
    expect(routerSpy.navigate).toHaveBeenCalledWith(['/']);
    expect(component.success).toBe(true);
  });

  it('should handle login error', () => {
    authServiceSpy.login.mockReturnValue(throwError(() => new Error('Failed')));

    component.loginForm.setValue({
      login: 'admin',
      password: 'wrong-password',
    });
    component.onSubmit();

    expect(component.error).toBe('Login failed');
    expect(component.loading).toBe(false);
    expect(routerSpy.navigate).not.toHaveBeenCalled();
  });
});
