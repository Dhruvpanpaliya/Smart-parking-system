import { Component, OnInit } from '@angular/core';
import { ReportService } from '../../services/report.service';

@Component({
  selector: 'app-reports',
  templateUrl: './reports.component.html'
})
export class ReportsComponent implements OnInit {
  stats: any = {};
  bookings: any[] = [];
  payments: any[] = [];
  totalRevenue = 0;
  loading = true;
  activeTab = 'overview';

  constructor(private reportService: ReportService) {}

  ngOnInit(): void {
    this.loadAll();
  }

  loadAll(): void {
    this.reportService.getDashboardStats().subscribe({
      next: (res) => { this.stats = res.data || {}; this.loading = false; }
    });
    this.reportService.getBookingsReport().subscribe({
      next: (res) => { this.bookings = res.data || []; }
    });
    this.reportService.getPaymentsReport().subscribe({
      next: (res) => { this.payments = res.data || []; }
    });
    this.reportService.getRevenueReport().subscribe({
      next: (res) => { this.totalRevenue = res.data || 0; }
    });
  }

  getStatusBadge(status: string): string {
    const map: any = { BOOKED: 'badge-booked', ENTERED: 'badge-entered', EXITED: 'badge-exited',
      PAID: 'badge-paid', TOWED: 'badge-towed', AVAILABLE: 'badge-available', PENDING: 'badge-pending' };
    return 'badge-status ' + (map[status] || 'badge-booked');
  }
}
