export interface LoginRequest {
  email: string;
  password: string;
}

export interface RegisterClientRequest {
  first_name: string;
  last_name: string;
  email: string;
  password: string;
  phone?: string;
  address?: string;
}

export interface LoginResponse {
  success: boolean;
  message: string;
  data: {
    user?: {
      id: string;
      name: string;
      email: string;
      role: string;
    };
    client_id?: string;
    first_name?: string;
    last_name?: string;
    email?: string;
    token: string;
  };
}

export interface User {
  id: string;
  name: string;
  email: string;
  role: 'admin' | 'client';
  token: string;
}
