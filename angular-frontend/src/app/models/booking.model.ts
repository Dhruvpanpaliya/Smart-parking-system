export interface BookingRequest {
  userId: number;
  slotId: number;
  vehicleNumber: string;
}

export interface BookingResponse {
  bookingId: number;
  userName: string;
  slotNumber: string;
  locationName: string;
  vehicleNumber: string;
  vehicleType: string;
  status: string;
  bookingTime: string;
  entryTime: string;
  exitTime: string;
  qrCode: string;
}
