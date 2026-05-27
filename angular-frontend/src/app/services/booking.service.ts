import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ApiResponse } from '../models/api-response.model';
import { BookingRequest } from '../models/booking.model';

@Injectable({ providedIn: 'root' })
export class BookingService {
  private apiUrl = 'http://localhost:8080/api';

  constructor(private http: HttpClient) {}

  createBooking(req: BookingRequest): Observable<ApiResponse> {
    return this.http.post<ApiResponse>(`${this.apiUrl}/bookings`, req);
  }

  getAllBookings(): Observable<ApiResponse> {
    return this.http.get<ApiResponse>(`${this.apiUrl}/bookings`);
  }

  getBookingById(id: number): Observable<ApiResponse> {
    return this.http.get<ApiResponse>(`${this.apiUrl}/bookings/${id}`);
  }

  getBookingsByUser(userId: number): Observable<ApiResponse> {
    return this.http.get<ApiResponse>(`${this.apiUrl}/bookings/user/${userId}`);
  }

  cancelBooking(id: number): Observable<ApiResponse> {
    return this.http.delete<ApiResponse>(`${this.apiUrl}/bookings/${id}`);
  }

  processEntry(bookingId: number): Observable<ApiResponse> {
    return this.http.post<ApiResponse>(`${this.apiUrl}/entry`, { bookingId });
  }

  processExit(bookingId: number): Observable<ApiResponse> {
    return this.http.post<ApiResponse>(`${this.apiUrl}/exit`, { bookingId });
  }
}
