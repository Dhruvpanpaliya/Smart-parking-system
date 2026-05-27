import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html'
})
export class RegisterComponent {
  name = '';
  email = '';
  mobile = '';
  password = '';
  vehicleNumber = '';
  errorMsg = '';
  successMsg = '';
  loading = false;

  constructor(private authService: AuthService, private router: Router) {}

  onRegister(): void {
    this.errorMsg = '';
    this.successMsg = '';
    if (!this.name || !this.email || !this.mobile || !this.password || !this.vehicleNumber) {
      this.errorMsg = 'All fields are required';
      return;
    }
    if (this.password.length < 6) {
      this.errorMsg = 'Password must be at least 6 characters';
      return;
    }
    this.loading = true;
    this.authService.register({
      name: this.name, email: this.email, mobile: this.mobile,
      password: this.password, vehicleNumber: this.vehicleNumber
    }).subscribe({
      next: (res) => {
        this.loading = false;
        if (res.success) {
          this.successMsg = 'Registration successful! Redirecting to login...';
          setTimeout(() => this.router.navigate(['/login']), 2000);
        } else {
          this.errorMsg = res.message;
        }
      },
      error: (err) => {
        this.loading = false;
        this.errorMsg = err.error?.message || 'Registration failed. Please try again.';
      }
    });
  }
}
