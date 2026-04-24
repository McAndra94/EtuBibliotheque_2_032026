import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { EtudiantService } from '../../services/etudiant.service';

export interface Etudiant {
  id?: number;
  firstName: string;
  lastName: string;
  login?: string;
}

@Component({
  selector: 'app-etudiant',
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './etudiant.component.html',
  styleUrls: ['./etudiant.component.css'],
})
export class EtudiantComponent implements OnInit {
  etudiants: Etudiant[] = [];

  form: Etudiant = { firstName: '', lastName: '', login: '' };
  isEdit = false;

  constructor(private etudiantService: EtudiantService) {}

  ngOnInit(): void {
    this.loadEtudiants();
  }

  loadEtudiants(): void {
    this.etudiantService.getAll().subscribe((data) => {
      this.etudiants = data;
    });
  }

  submit(): void {
    if (this.isEdit && this.form.id) {
      this.etudiantService.update(this.form.id, this.form).subscribe(() => {
        this.resetForm();
        this.loadEtudiants();
      });
    } else {
      this.etudiantService.create(this.form).subscribe(() => {
        this.resetForm();
        this.loadEtudiants();
      });
    }
  }

  edit(e: Etudiant): void {
    this.form = { ...e };
    this.isEdit = true;
  }

  delete(id: number): void {
    this.etudiantService.delete(id).subscribe(() => {
      this.etudiants = this.etudiants.filter((et) => et.id !== id);
    });
  }

  resetForm(): void {
    this.form = { firstName: '', lastName: '', login: '' };
    this.isEdit = false;
  }
}
