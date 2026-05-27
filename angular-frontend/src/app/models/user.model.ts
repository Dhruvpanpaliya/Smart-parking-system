export interface User {
  id: number;
  name: string;
  email: string;
  mobile: string;
  vehicleNumber: string;
  role: string;
  createdAt: string;
}

export interface LoginRequest {
  email: string;
  password: string;
}

export interface RegisterRequest {
  name: string;
  email: string;
  mobile: string;
  password: string;
  vehicleNumber: string;
}
