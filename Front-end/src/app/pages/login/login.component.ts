import { Component } from '@angular/core';
import {
  FormBuilder,
  FormGroup,
  Validators,
  ReactiveFormsModule,
} from '@angular/forms';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { AuthService } from './../../services/auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  standalone: true,
  imports: [ReactiveFormsModule, CommonModule],
})
export class LoginComponent {
  loginForm: FormGroup;

  loading = false;
  error: string | null = null;
  success = false;

  constructor(
    private authService: AuthService,
    private formBuilder: FormBuilder,
    private router: Router,
  ) {
    this.loginForm = this.formBuilder.group({
      login: ['', Validators.required],
      password: ['', Validators.required],
    });
  }
  ngOnInit() {
    console.log('Login component loaded');
  }
  get form() {
    return this.loginForm.controls;
  }
  onSubmit() {
    if (this.loginForm.invalid) return;

    console.log('SUBMIT CLICKED');

    this.loading = true;
    this.error = null;

    //const { login, password } = this.loginForm.value;
    const login = this.loginForm.get('login')?.value;
    const password = this.loginForm.get('password')?.value;
    console.log('Checking:', { login, password });
    // Data in backend
    this.authService.login(login, password).subscribe({
      next: (token: string) => {
        console.log('SUCCESS TOKEN:', token);

        this.loading = false;
        this.success = true;

        localStorage.setItem('token', token);
        this.router.navigate(['/']);
      },
      error: (err) => {
        this.loading = false;
        console.log(err);
        this.error = 'Login failed';
      },
    });
  }
}
