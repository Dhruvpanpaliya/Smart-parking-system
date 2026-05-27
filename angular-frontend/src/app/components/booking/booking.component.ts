import { Component, OnInit } from '@angular/core';
import { AuthService } from '../../services/auth.service';
import { BookingService } from '../../services/booking.service';
import { SlotService } from '../../services/slot.service';

@Component({
  selector: 'app-booking',
  templateUrl: './booking.component.html'
})
export class BookingComponent implements OnInit {
  user: any;
  locations: any[] = [];
  availableSlots: any[] = [];
  myBookings: any[] = [];
  allBookings: any[] = [];

  selectedLocationId: number | null = null;
  selectedVehicleType = '';
  selectedSlot: any = null;
  activeTab = 'book'; // 'book' or 'mybookings'

  successMsg = '';
  errorMsg = '';
  loading = false;
  qrCodeData = '';

  constructor(
    public authService: AuthService,
    private bookingService: BookingService,
    private slotService: SlotService
  ) {}

  ngOnInit(): void {
    this.user = this.authService.getUser();
    this.loadLocations();
    this.loadMyBookings();
    if (this.authService.isAdmin()) {
      this.bookingService.getAllBookings().subscribe({
        next: (res) => { this.allBookings = res.data || []; }
      });
    }
  }

  loadLocations(): void {
    this.slotService.getActiveLocations().subscribe({
      next: (res) => { this.locations = res.data || []; }
    });
  }

  loadMyBookings(): void {
    if (this.user) {
      this.bookingService.getBookingsByUser(this.user.id).subscribe({
        next: (res) => { this.myBookings = res.data || []; }
      });
    }
  }

  searchSlots(): void {
    this.availableSlots = [];
    this.selectedSlot = null;
    if (!this.selectedLocationId) return;

    const obs = this.selectedVehicleType
      ? this.slotService.getAvailableSlotsByLocationAndType(this.selectedLocationId, this.selectedVehicleType)
      : this.slotService.getAvailableSlotsByLocation(this.selectedLocationId);

    obs.subscribe({
      next: (res) => { this.availableSlots = res.data || []; }
    });
  }

  selectSlot(slot: any): void {
    this.selectedSlot = slot;
  }

  confirmBooking(): void {
    if (!this.selectedSlot || !this.user) return;
    this.loading = true;
    this.errorMsg = '';
    this.successMsg = '';
    this.qrCodeData = '';

    this.bookingService.createBooking({
      userId: this.user.id,
      slotId: this.selectedSlot.id,
      vehicleNumber: this.user.vehicleNumber
    }).subscribe({
      next: (res) => {
        this.loading = false;
        if (res.success) {
          this.successMsg = 'Booking created successfully!';
          this.qrCodeData = res.data.qrCode || '';
          this.selectedSlot = null;
          this.availableSlots = [];
          this.loadMyBookings();
        } else {
          this.errorMsg = res.message;
        }
      },
      error: (err) => {
        this.loading = false;
        this.errorMsg = err.error?.message || 'Booking failed';
      }
    });
  }

  cancelBooking(id: number): void {
    if (!confirm('Cancel this booking?')) return;
    this.bookingService.cancelBooking(id).subscribe({
      next: () => { this.loadMyBookings(); this.successMsg = 'Booking cancelled.'; },
      error: (err) => { this.errorMsg = err.error?.message || 'Cancel failed'; }
    });
  }

  getStatusBadge(status: string): string {
    const map: any = { BOOKED: 'badge-booked', ENTERED: 'badge-entered', EXITED: 'badge-exited',
      PAID: 'badge-paid', TOWED: 'badge-towed' };
    return 'badge-status ' + (map[status] || 'badge-booked');
  }
}
