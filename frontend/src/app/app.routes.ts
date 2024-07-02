import { Routes } from '@angular/router';
import { LoginComponent } from "./components/auth/login/login.component";
import { RegisterComponent } from "./components/auth/register/register.component";
import { AdminDashboardComponent } from "./components/admin/admin-dashboard/admin-dashboard.component";
import { AuthGuard } from "./guards/auth.guard";

export const routes: Routes = [
    { path: '', redirectTo: 'auth/login', pathMatch: "full" },
    { path: 'auth/login', component: LoginComponent },
    { path: 'auth/register', component: RegisterComponent },
    { path: 'adashboard', component: AdminDashboardComponent, canActivate: [AuthGuard], data: { role: 'ADMIN' } },
    { path: '**', redirectTo: 'auth/login' },
];
