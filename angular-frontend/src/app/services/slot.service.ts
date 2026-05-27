import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ApiResponse } from '../models/api-response.model';

@Injectable({ providedIn: 'root' })
export class SlotService {
  private apiUrl = 'http://localhost:8080/api';

  constructor(private http: HttpClient) {}

  getAllSlots(): Observable<ApiResponse> {
    return this.http.get<ApiResponse>(`${this.apiUrl}/slots`);
  }

  getSlotById(id: number): Observable<ApiResponse> {
    return this.http.get<ApiResponse>(`${this.apiUrl}/slots/${id}`);
  }

  getSlotsByLocation(locationId: number): Observable<ApiResponse> {
    return this.http.get<ApiResponse>(`${this.apiUrl}/slots/location/${locationId}`);
  }

  getAvailableSlots(): Observable<ApiResponse> {
    return this.http.get<ApiResponse>(`${this.apiUrl}/slots/available`);
  }

  getAvailableSlotsByLocation(locationId: number): Observable<ApiResponse> {
    return this.http.get<ApiResponse>(`${this.apiUrl}/slots/available/${locationId}`);
  }

  getAvailableSlotsByLocationAndType(locationId: number, type: string): Observable<ApiResponse> {
    return this.http.get<ApiResponse>(`${this.apiUrl}/slots/available/${locationId}/${type}`);
  }

  createSlot(slot: any, locationId: number): Observable<ApiResponse> {
    return this.http.post<ApiResponse>(`${this.apiUrl}/slots?locationId=${locationId}`, slot);
  }

  updateSlot(id: number, slot: any): Observable<ApiResponse> {
    return this.http.put<ApiResponse>(`${this.apiUrl}/slots/${id}`, slot);
  }

  deleteSlot(id: number): Observable<ApiResponse> {
    return this.http.delete<ApiResponse>(`${this.apiUrl}/slots/${id}`);
  }

  // Location APIs
  getAllLocations(): Observable<ApiResponse> {
    return this.http.get<ApiResponse>(`${this.apiUrl}/locations`);
  }

  getActiveLocations(): Observable<ApiResponse> {
    return this.http.get<ApiResponse>(`${this.apiUrl}/locations/active`);
  }

  createLocation(location: any): Observable<ApiResponse> {
    return this.http.post<ApiResponse>(`${this.apiUrl}/locations`, location);
  }

  updateLocation(id: number, location: any): Observable<ApiResponse> {
    return this.http.put<ApiResponse>(`${this.apiUrl}/locations/${id}`, location);
  }

  deleteLocation(id: number): Observable<ApiResponse> {
    return this.http.delete<ApiResponse>(`${this.apiUrl}/locations/${id}`);
  }
}
