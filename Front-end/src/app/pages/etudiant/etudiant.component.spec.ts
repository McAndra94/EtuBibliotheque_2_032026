import { ComponentFixture, TestBed } from '@angular/core/testing';
import { EtudiantComponent } from './etudiant.component';
import { EtudiantService } from '../../services/etudiant.service';
import { of } from 'rxjs';
import { FormsModule } from '@angular/forms';

describe('EtudiantComponent', () => {
  let component: EtudiantComponent;
  let fixture: ComponentFixture<EtudiantComponent>;
  let mockEtudiantService: any;

  beforeEach(async () => {
    mockEtudiantService = {
      getAll: jest.fn().mockReturnValue(of([])),
      create: jest.fn().mockReturnValue(of({})),
      update: jest.fn().mockReturnValue(of({})),
      delete: jest.fn().mockReturnValue(of({})),
    };

    await TestBed.configureTestingModule({
      imports: [EtudiantComponent, FormsModule],
      providers: [{ provide: EtudiantService, useValue: mockEtudiantService }],
    }).compileComponents();

    fixture = TestBed.createComponent(EtudiantComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should load students on init', () => {
    const mockData = [{ id: 1, firstName: 'John', lastName: 'Doe' }];
    mockEtudiantService.getAll.mockReturnValue(of(mockData));

    component.ngOnInit();
    expect(component.etudiants).toEqual(mockData);
  });

  it('should create a new student when submit is called (isEdit = false)', () => {
    component.isEdit = false;
    const studentData = { firstName: 'Tony', lastName: 'Fred' };
    component.form = { ...studentData };

    component.submit();

    expect(mockEtudiantService.create).toHaveBeenCalledWith(
      expect.objectContaining(studentData),
    );
    expect(component.isEdit).toBe(false);
  });

  it('should update an existing student when submit is called (isEdit = true)', () => {
    component.isEdit = true;
    const studentData = { id: 1, firstName: 'Alex', lastName: 'Test' };
    component.form = { ...studentData };

    component.submit();

    expect(mockEtudiantService.update).toHaveBeenCalledWith(
      1,
      expect.objectContaining(studentData),
    );
    expect(component.isEdit).toBe(false);
  });

  it('should set form and isEdit mode when editing', () => {
    const etudiant = { id: 1, firstName: 'Alice', lastName: 'Tony' };
    component.edit(etudiant);

    expect(component.form).toEqual(etudiant);
    expect(component.isEdit).toBe(true);
  });

  it('should delete a student and filter the list', () => {
    component.etudiants = [
      {
        id: 1,
        firstName: 'A',
        lastName: '',
      },
      {
        id: 2,
        firstName: 'B',
        lastName: '',
      },
    ];

    component.delete(1);

    expect(mockEtudiantService.delete).toHaveBeenCalledWith(1);
    expect(component.etudiants.length).toBe(1);
    expect(component.etudiants[0].id).toBe(2);
  });

  it('should reset form state', () => {
    component.form = { firstName: 'X', lastName: 'Y', login: 'Z' };
    component.isEdit = true;

    component.resetForm();

    expect(component.form.firstName).toBe('');
    expect(component.isEdit).toBe(false);
  });
});
