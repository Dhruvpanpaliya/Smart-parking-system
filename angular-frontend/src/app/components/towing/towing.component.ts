import { Component, OnInit } from '@angular/core';
import { ReportService } from '../../services/report.service';

@Component({
  selector: 'app-towing',
  templateUrl: './towing.component.html'
})
export class TowingComponent implements OnInit {
  towingRequests: any[] = [];
  detectedBookings: any[] = [];
  successMsg = '';
  errorMsg = '';
  loading = true;

  // Towing request form
  towingForm: any = { bookingId: null, towingLocation: '', contactInfo: '', reason: '' };

  constructor(private reportService: ReportService) {}

  ngOnInit(): void {
    this.loadTowingRequests();
  }

  loadTowingRequests(): void {
    this.reportService.getAllTowingRequests().subscribe({
      next: (res) => { this.towingRequests = res.data || []; this.loading = false; },
      error: () => { this.loading = false; }
    });
  }

  detectOverstayed(): void {
    this.errorMsg = ''; this.successMsg = '';
    this.reportService.detectOverstayed().subscribe({
      next: (res) => {
        this.detectedBookings = res.data || [];
        this.successMsg = `Detected ${this.detectedBookings.length} overstayed vehicle(s)`;
      },
      error: (err) => { this.errorMsg = err.error?.message || 'Detection failed'; }
    });
  }

  createTowingRequest(): void {
    this.errorMsg = ''; this.successMsg = '';
    if (!this.towingForm.bookingId || !this.towingForm.towingLocation) {
      this.errorMsg = 'Booking ID and towing location are required';
      return;
    }
    this.reportService.createTowingRequest(this.towingForm.bookingId, {
      towingLocation: this.towingForm.towingLocation,
      contactInfo: this.towingForm.contactInfo,
      reason: this.towingForm.reason
    }).subscribe({
      next: () => {
        this.successMsg = 'Towing request created!';
        this.towingForm = { bookingId: null, towingLocation: '', contactInfo: '', reason: '' };
        this.loadTowingRequests();
      },
      error: (err) => { this.errorMsg = err.error?.message || 'Creation failed'; }
    });
  }
}
