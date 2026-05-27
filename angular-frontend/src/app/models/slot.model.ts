export interface Slot {
  id: number;
  slotNumber: string;
  vehicleType: string;
  status: string;
  parkingLocation: ParkingLocation;
}

export interface ParkingLocation {
  id: number;
  name: string;
  address: string;
  totalSlots: number;
  active: boolean;
}
