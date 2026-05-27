import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from './services/auth.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  sidebarOpen = true;

  constructor(public authService: AuthService, private router: Router) {}

  isLoggedIn(): boolean { return this.authService.isLoggedIn(); }
  isAdmin(): boolean { return this.authService.isAdmin(); }
  getUser() { return this.authService.getUser(); }

  getUserInitial(): string {
    const user = this.getUser();
    return user ? user.name.charAt(0).toUpperCase() : '?';
  }

  logout(): void {
    this.authService.logout();
    this.router.navigate(['/login']);
  }

  isAuthPage(): boolean {
    return this.router.url === '/login' || this.router.url === '/register';
  }
}
