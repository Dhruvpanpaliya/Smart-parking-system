import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ApiResponse } from '../models/api-response.model';

@Injectable({ providedIn: 'root' })
export class PaymentService {
  private apiUrl = 'http://localhost:8080/api';

  constructor(private http: HttpClient) {}

  processPayment(bookingId: number): Observable<ApiResponse> {
    return this.http.post<ApiResponse>(`${this.apiUrl}/payments`, { bookingId });
  }

  getPaymentByBooking(bookingId: number): Observable<ApiResponse> {
    return this.http.get<ApiResponse>(`${this.apiUrl}/payments/booking/${bookingId}`);
  }

  getAllPayments(): Observable<ApiResponse> {
    return this.http.get<ApiResponse>(`${this.apiUrl}/payments`);
  }

  getTotalRevenue(): Observable<ApiResponse> {
    return this.http.get<ApiResponse>(`${this.apiUrl}/payments/revenue`);
  }
}
