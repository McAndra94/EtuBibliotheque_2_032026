import { TestBed } from '@angular/core/testing';
import { Router } from '@angular/router';
import { expect } from '@jest/globals';
import { authGuard } from './auth.guard';

describe('authGuard', () => {
  let routerMock: any;

  beforeEach(() => {
    routerMock = {
      navigate: jest.fn(),
    };

    Object.defineProperty(window, 'localStorage', {
      value: {
        getItem: jest.fn(),
      },
      writable: true,
    });

    TestBed.configureTestingModule({
      providers: [{ provide: Router, useValue: routerMock }],
    });
  });

  it('should redirect to /login and return false if token is missing', () => {
    (localStorage.getItem as jest.Mock).mockReturnValue(null);

    const result = TestBed.runInInjectionContext(() =>
      authGuard({} as any, {} as any),
    );

    expect(result).toBe(false);
    expect(routerMock.navigate).toHaveBeenCalledWith(['/login']);
  });

  it('should return true if token exists', () => {
    (localStorage.getItem as jest.Mock).mockReturnValue('fake-token');

    const result = TestBed.runInInjectionContext(() =>
      authGuard({} as any, {} as any),
    );

    expect(result).toBe(true);
    expect(routerMock.navigate).not.toHaveBeenCalled();
  });
});
