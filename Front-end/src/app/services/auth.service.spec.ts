import { TestBed } from '@angular/core/testing';
import { expect, describe, it } from '@jest/globals';
import {
  HttpClientTestingModule,
  HttpTestingController,
} from '@angular/common/http/testing';
import { AuthService } from './auth.service';

describe('AuthService', () => {
  let service: AuthService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [AuthService],
    });
    service = TestBed.inject(AuthService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('should return token on success', (done) => {
    service.login('admin', 'password').subscribe((token) => {
      expect(token).toEqual('my-token');
      done();
    });

    const req = httpMock.expectOne('http://localhost:8080/api/login');
    req.flush('my-token'); // Simulates valid token
  });

  it('should throw error when response is empty', (done) => {
    service.login('admin', 'password').subscribe({
      error: (err) => {
        expect(err.message).toEqual('Empty login response');
        done();
      },
    });

    const req = httpMock.expectOne('http://localhost:8080/api/login');
    req.flush('');
  });
});
