import { Component, OnInit } from '@angular/core';
import { AuthService } from '../../services/auth.service';
import { ReportService } from '../../services/report.service';
import { BookingService } from '../../services/booking.service';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html'
})
export class DashboardComponent implements OnInit {
  user: any;
  stats: any = {};
  recentBookings: any[] = [];
  loading = true;

  // Security staff entry/exit
  entryBookingId: number | null = null;
  exitBookingId: number | null = null;
  entryMsg = '';
  exitMsg = '';
  entryError = '';
  exitError = '';

  constructor(
    public authService: AuthService,
    private reportService: ReportService,
    private bookingService: BookingService
  ) {}

  ngOnInit(): void {
    this.user = this.authService.getUser();
    if (this.authService.isAdmin()) {
      this.loadAdminDashboard();
    } else {
      this.loadUserDashboard();
    }
  }

  loadAdminDashboard(): void {
    this.reportService.getDashboardStats().subscribe({
      next: (res) => {
        this.stats = res.data;
        this.loading = false;
      },
      error: () => { this.loading = false; }
    });
    this.bookingService.getAllBookings().subscribe({
      next: (res) => { this.recentBookings = (res.data || []).slice(0, 10); }
    });
  }

  loadUserDashboard(): void {
    if (this.user) {
      this.bookingService.getBookingsByUser(this.user.id).subscribe({
        next: (res) => {
          this.recentBookings = res.data || [];
          this.loading = false;
        },
        error: () => { this.loading = false; }
      });
    }
  }

  processEntry(): void {
    this.entryMsg = ''; this.entryError = '';
    if (!this.entryBookingId) { this.entryError = 'Enter Booking ID'; return; }
    this.bookingService.processEntry(this.entryBookingId).subscribe({
      next: (res) => { this.entryMsg = 'Entry recorded successfully!'; this.entryBookingId = null; },
      error: (err) => { this.entryError = err.error?.message || 'Entry failed'; }
    });
  }

  processExit(): void {
    this.exitMsg = ''; this.exitError = '';
    if (!this.exitBookingId) { this.exitError = 'Enter Booking ID'; return; }
    this.bookingService.processExit(this.exitBookingId).subscribe({
      next: (res) => { this.exitMsg = 'Exit recorded successfully!'; this.exitBookingId = null; },
      error: (err) => { this.exitError = err.error?.message || 'Exit failed'; }
    });
  }

  getStatusBadge(status: string): string {
    const map: any = { AVAILABLE: 'badge-available', BOOKED: 'badge-booked', OCCUPIED: 'badge-occupied',
      ENTERED: 'badge-entered', EXITED: 'badge-exited', PAID: 'badge-paid', TOWED: 'badge-towed', PENDING: 'badge-pending' };
    return 'badge-status ' + (map[status] || 'badge-booked');
  }
}
