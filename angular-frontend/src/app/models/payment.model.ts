export interface PaymentResponse {
  paymentId: number;
  bookingId: number;
  amount: number;
  status: string;
  paymentTime: string;
}
