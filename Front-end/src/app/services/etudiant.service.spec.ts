import { TestBed } from '@angular/core/testing';
import {
  HttpClientTestingModule,
  HttpTestingController,
} from '@angular/common/http/testing';
import { EtudiantService } from './etudiant.service';
import { Etudiant } from '../pages/etudiant/etudiant.component';

describe('EtudiantService', () => {
  let service: EtudiantService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [EtudiantService],
    });

    service = TestBed.inject(EtudiantService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => httpMock.verify());

  it('should get all students', () => {
    const mockEtudiants: Etudiant[] = [
      { id: 1, firstName: 'John', lastName: 'Doe', login: 'login' },
    ];

    service.getAll().subscribe((data) => {
      expect(data.length).toBe(1);
      expect(data[0].firstName).toBe('John');
    });

    const req = httpMock.expectOne('http://localhost:8080/api/etudiants');
    expect(req.request.method).toBe('GET');
    req.flush(mockEtudiants);
  });

  it('should get one student', () => {
    const mockEtudiant: Etudiant = {
      id: 1,
      firstName: 'John',
      lastName: 'Doe',
      login: 'login',
    };

    service.getOne(1).subscribe((data) => {
      expect(data.id).toBe(1);
    });

    const req = httpMock.expectOne('http://localhost:8080/api/etudiants/1');
    expect(req.request.method).toBe('GET');
    req.flush(mockEtudiant);
  });

  it('should create a student', () => {
    const newEtudiant: Etudiant = {
      id: 1,
      firstName: 'John',
      lastName: 'Doe',
      login: 'login',
    };

    service.create(newEtudiant).subscribe((data) => {
      expect(data.id).toBe(1);
    });

    const req = httpMock.expectOne('http://localhost:8080/api/etudiants');
    expect(req.request.method).toBe('POST');
    req.flush(newEtudiant);
  });

  it('should update a student', () => {
    const updated: Etudiant = {
      id: 1,
      firstName: 'New',
      lastName: 'Name',
      login: 'newLogin',
    };

    service.update(1, updated).subscribe((data) => {
      expect(data.firstName).toBe('New');
    });

    const req = httpMock.expectOne('http://localhost:8080/api/etudiants/1');
    expect(req.request.method).toBe('PUT');
    req.flush(updated);
  });

  it('should delete a student', () => {
    service.delete(1).subscribe((response) => {
      expect(response).toBeUndefined();
    });

    const req = httpMock.expectOne('http://localhost:8080/api/etudiants/1');
    expect(req.request.method).toBe('DELETE');
    req.flush(null);
  });
});
