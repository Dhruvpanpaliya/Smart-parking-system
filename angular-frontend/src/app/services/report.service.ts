import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ApiResponse } from '../models/api-response.model';

@Injectable({ providedIn: 'root' })
export class ReportService {
  private apiUrl = 'http://localhost:8080/api';

  constructor(private http: HttpClient) {}

  getDashboardStats(): Observable<ApiResponse> {
    return this.http.get<ApiResponse>(`${this.apiUrl}/reports/dashboard`);
  }

  getBookingsReport(): Observable<ApiResponse> {
    return this.http.get<ApiResponse>(`${this.apiUrl}/reports/bookings`);
  }

  getPaymentsReport(): Observable<ApiResponse> {
    return this.http.get<ApiResponse>(`${this.apiUrl}/reports/payments`);
  }

  getRevenueReport(): Observable<ApiResponse> {
    return this.http.get<ApiResponse>(`${this.apiUrl}/reports/revenue`);
  }

  // Towing APIs
  detectOverstayed(): Observable<ApiResponse> {
    return this.http.post<ApiResponse>(`${this.apiUrl}/towing/detect`, {});
  }

  createTowingRequest(bookingId: number, request: any): Observable<ApiResponse> {
    return this.http.post<ApiResponse>(`${this.apiUrl}/towing/${bookingId}`, request);
  }

  getAllTowingRequests(): Observable<ApiResponse> {
    return this.http.get<ApiResponse>(`${this.apiUrl}/towing`);
  }

  getTowingByBooking(bookingId: number): Observable<ApiResponse> {
    return this.http.get<ApiResponse>(`${this.apiUrl}/towing/booking/${bookingId}`);
  }
}
