import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { map } from 'rxjs/operators';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private apiUrl = 'http://localhost:8080/api/login';

  constructor(private http: HttpClient) {}

  login(login: string, password: string): Observable<string> {
    return this.http
      .post(this.apiUrl, { login, password }, { responseType: 'text' })
      .pipe(
        map((resp: string) => {
          if (!resp) throw new Error('Empty login response');
          return resp;
        }),
      );
  }

  saveToken(token: string) {
    localStorage.setItem('token', token);
  }

  getToken(): string | null {
    return localStorage.getItem('token');
  }

  logout() {
    localStorage.removeItem('token');
  }
}
