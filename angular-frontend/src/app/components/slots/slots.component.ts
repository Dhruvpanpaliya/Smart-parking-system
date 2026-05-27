import { Component, OnInit } from '@angular/core';
import { SlotService } from '../../services/slot.service';

@Component({
  selector: 'app-slots',
  templateUrl: './slots.component.html'
})
export class SlotsComponent implements OnInit {
  activeTab = 'locations';
  locations: any[] = [];
  slots: any[] = [];
  filterLocationId: number | null = null;

  // Location form
  locForm: any = { name: '', address: '', totalSlots: 1, active: true };
  editingLocId: number | null = null;

  // Slot form
  slotForm: any = { slotNumber: '', vehicleType: 'CAR', locationId: null };
  editingSlotId: number | null = null;

  successMsg = '';
  errorMsg = '';

  constructor(private slotService: SlotService) {}

  ngOnInit(): void {
    this.loadLocations();
    this.loadSlots();
  }

  loadLocations(): void {
    this.slotService.getAllLocations().subscribe({
      next: (res) => { this.locations = res.data || []; }
    });
  }

  loadSlots(): void {
    this.slotService.getAllSlots().subscribe({
      next: (res) => { this.slots = res.data || []; }
    });
  }

  // ===== Location CRUD =====
  saveLocation(): void {
    this.errorMsg = ''; this.successMsg = '';
    if (!this.locForm.name || !this.locForm.address) { this.errorMsg = 'Name and address required'; return; }

    const obs = this.editingLocId
      ? this.slotService.updateLocation(this.editingLocId, this.locForm)
      : this.slotService.createLocation(this.locForm);

    obs.subscribe({
      next: () => {
        this.successMsg = this.editingLocId ? 'Location updated!' : 'Location created!';
        this.resetLocForm();
        this.loadLocations();
      },
      error: (err) => { this.errorMsg = err.error?.message || 'Operation failed'; }
    });
  }

  editLocation(loc: any): void {
    this.editingLocId = loc.id;
    this.locForm = { name: loc.name, address: loc.address, totalSlots: loc.totalSlots, active: loc.active };
  }

  deleteLocation(id: number): void {
    if (!confirm('Delete this location?')) return;
    this.slotService.deleteLocation(id).subscribe({
      next: () => { this.loadLocations(); this.successMsg = 'Location deleted'; },
      error: (err) => { this.errorMsg = err.error?.message || 'Delete failed'; }
    });
  }

  resetLocForm(): void {
    this.locForm = { name: '', address: '', totalSlots: 1, active: true };
    this.editingLocId = null;
  }

  // ===== Slot CRUD =====
  saveSlot(): void {
    this.errorMsg = ''; this.successMsg = '';
    if (!this.slotForm.slotNumber || !this.slotForm.locationId) {
      this.errorMsg = 'Slot number and location are required'; return;
    }

    if (this.editingSlotId) {
      this.slotService.updateSlot(this.editingSlotId, this.slotForm).subscribe({
        next: () => { this.successMsg = 'Slot updated!'; this.resetSlotForm(); this.loadSlots(); },
        error: (err) => { this.errorMsg = err.error?.message || 'Update failed'; }
      });
    } else {
      this.slotService.createSlot(
        { slotNumber: this.slotForm.slotNumber, vehicleType: this.slotForm.vehicleType, status: 'AVAILABLE' },
        this.slotForm.locationId
      ).subscribe({
        next: () => { this.successMsg = 'Slot created!'; this.resetSlotForm(); this.loadSlots(); },
        error: (err) => { this.errorMsg = err.error?.message || 'Create failed'; }
      });
    }
  }

  editSlot(slot: any): void {
    this.editingSlotId = slot.id;
    this.slotForm = {
      slotNumber: slot.slotNumber,
      vehicleType: slot.vehicleType,
      locationId: slot.parkingLocation?.id
    };
  }

  deleteSlot(id: number): void {
    if (!confirm('Delete this slot?')) return;
    this.slotService.deleteSlot(id).subscribe({
      next: () => { this.loadSlots(); this.successMsg = 'Slot deleted'; },
      error: (err) => { this.errorMsg = err.error?.message || 'Delete failed'; }
    });
  }

  resetSlotForm(): void {
    this.slotForm = { slotNumber: '', vehicleType: 'CAR', locationId: null };
    this.editingSlotId = null;
  }

  getFilteredSlots(): any[] {
    if (!this.filterLocationId) return this.slots;
    return this.slots.filter(s => s.parkingLocation?.id === this.filterLocationId);
  }

  getStatusBadge(status: string): string {
    const map: any = { AVAILABLE: 'badge-available', BOOKED: 'badge-booked', OCCUPIED: 'badge-occupied' };
    return 'badge-status ' + (map[status] || 'badge-booked');
  }
}
