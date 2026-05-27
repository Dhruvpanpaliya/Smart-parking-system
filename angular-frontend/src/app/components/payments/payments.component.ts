import { Component, OnInit } from '@angular/core';
import { AuthService } from '../../services/auth.service';
import { PaymentService } from '../../services/payment.service';
import { BookingService } from '../../services/booking.service';

@Component({
  selector: 'app-payments',
  templateUrl: './payments.component.html'
})
export class PaymentsComponent implements OnInit {
  user: any;
  allPayments: any[] = [];
  myBookings: any[] = [];
  totalRevenue = 0;
  successMsg = '';
  errorMsg = '';
  loading = true;

  constructor(
    public authService: AuthService,
    private paymentService: PaymentService,
    private bookingService: BookingService
  ) {}

  ngOnInit(): void {
    this.user = this.authService.getUser();
    if (this.authService.isAdmin()) {
      this.loadAllPayments();
      this.loadRevenue();
    }
    this.loadMyBookings();
  }

  loadAllPayments(): void {
    this.paymentService.getAllPayments().subscribe({
      next: (res) => { this.allPayments = res.data || []; this.loading = false; },
      error: () => { this.loading = false; }
    });
  }

  loadRevenue(): void {
    this.paymentService.getTotalRevenue().subscribe({
      next: (res) => { this.totalRevenue = res.data || 0; }
    });
  }

  loadMyBookings(): void {
    if (this.user) {
      this.bookingService.getBookingsByUser(this.user.id).subscribe({
        next: (res) => { this.myBookings = res.data || []; this.loading = false; },
        error: () => { this.loading = false; }
      });
    }
  }

  getExitedBookings(): any[] {
    return this.myBookings.filter(b => b.status === 'EXITED');
  }

  getPaidBookings(): any[] {
    return this.myBookings.filter(b => b.status === 'PAID');
  }

  makePayment(bookingId: number): void {
    this.errorMsg = ''; this.successMsg = '';
    this.paymentService.processPayment(bookingId).subscribe({
      next: (res) => {
        if (res.success) {
          this.successMsg = `Payment of ₹${res.data.amount} processed successfully!`;
          this.loadMyBookings();
          if (this.authService.isAdmin()) { this.loadAllPayments(); this.loadRevenue(); }
        }
      },
      error: (err) => { this.errorMsg = err.error?.message || 'Payment failed'; }
    });
  }
}
