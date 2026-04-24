import { Routes } from '@angular/router';
import { RegisterComponent } from './pages/register/register.component';
import { LoginComponent } from './pages/login/login.component';
import { EtudiantComponent } from './pages/etudiant/etudiant.component';
import { authGuard } from './core/guards/auth.guard';

export const routes: Routes = [
  { path: 'register', component: RegisterComponent },
  { path: 'login', component: LoginComponent },
  { path: 'etudiants', component: EtudiantComponent, canActivate: [authGuard] },

  { path: '', redirectTo: 'etudiants', pathMatch: 'full' },
  // 404
  { path: '**', redirectTo: 'etudiants' },
];
