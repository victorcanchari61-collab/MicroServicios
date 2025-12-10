import { Routes } from '@angular/router';
import { HomeComponent } from './features/home/home.component';
import { LoginComponent } from './features/auth/login/login.component';
import { RegisterComponent } from './features/auth/register/register.component';
import { DashboardComponent } from './features/admin/dashboard/dashboard.component';
import { RoomsComponent } from './features/admin/rooms/rooms';
import { AdminReservationsComponent } from './features/admin/reservations/reservations';
import { HotelsComponent } from './features/client/hotels/hotels.component';
import { MyReservationsComponent } from './features/client/my-reservations/my-reservations.component';

export const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'admin/dashboard', component: DashboardComponent },
  { path: 'admin/rooms/:hotelId', component: RoomsComponent },
  { path: 'admin/reservations', component: AdminReservationsComponent },
  { path: 'client/hotels', component: HotelsComponent },
  { path: 'client/my-reservations', component: MyReservationsComponent },
  { path: '**', redirectTo: '' }
];
