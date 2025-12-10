import { Injectable, inject, signal } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { Observable, tap } from 'rxjs';
import { environment } from '../../../environments/environment';
import { LoginRequest, RegisterClientRequest, LoginResponse, User } from '../models/auth.model';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private http = inject(HttpClient);
  private router = inject(Router);
  
  currentUser = signal<User | null>(null);
  isAuthenticated = signal<boolean>(false);

  constructor() {
    this.loadUserFromStorage();
  }

  private loadUserFromStorage(): void {
    if (typeof window !== 'undefined' && typeof localStorage !== 'undefined') {
      const userStr = localStorage.getItem('currentUser');
      if (userStr) {
        const user = JSON.parse(userStr);
        this.currentUser.set(user);
        this.isAuthenticated.set(true);
      }
    }
  }

  loginAdmin(credentials: LoginRequest): Observable<LoginResponse> {
    return this.http.post<LoginResponse>(`${environment.apiUrl}/admin/login`, credentials)
      .pipe(
        tap(response => {
          if (response.success && response.data.user) {
            const user: User = {
              id: response.data.user.id,
              name: response.data.user.name,
              email: response.data.user.email,
              role: 'admin',
              token: response.data.token
            };
            this.setCurrentUser(user);
          }
        })
      );
  }

  loginClient(credentials: LoginRequest): Observable<LoginResponse> {
    return this.http.post<LoginResponse>(`${environment.apiUrl}/clients/login`, credentials)
      .pipe(
        tap(response => {
          if (response.success && response.data.client_id) {
            const user: User = {
              id: response.data.client_id!,
              name: `${response.data.first_name} ${response.data.last_name}`,
              email: response.data.email!,
              role: 'client',
              token: response.data.token
            };
            this.setCurrentUser(user);
          }
        })
      );
  }

  registerClient(data: RegisterClientRequest): Observable<LoginResponse> {
    return this.http.post<LoginResponse>(`${environment.apiUrl}/clients/register`, data);
  }

  private setCurrentUser(user: User): void {
    this.currentUser.set(user);
    this.isAuthenticated.set(true);
    if (typeof window !== 'undefined' && typeof localStorage !== 'undefined') {
      localStorage.setItem('currentUser', JSON.stringify(user));
    }
  }

  logout(): void {
    this.currentUser.set(null);
    this.isAuthenticated.set(false);
    if (typeof window !== 'undefined' && typeof localStorage !== 'undefined') {
      localStorage.removeItem('currentUser');
    }
    this.router.navigate(['/']);
  }

  getToken(): string | null {
    return this.currentUser()?.token || null;
  }

  isAdmin(): boolean {
    return this.currentUser()?.role === 'admin';
  }

  isClient(): boolean {
    return this.currentUser()?.role === 'client';
  }
}
