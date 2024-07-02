import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, Router } from '@angular/router';

@Injectable({
    providedIn: 'root'
})
export class AuthGuard implements CanActivate {

    constructor(private router: Router) {
    }

    canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean {
        const expectedRole = route.data['role'];
        const role = localStorage.getItem('role');
        const token = localStorage.getItem('jwtToken');

        if (token && role === expectedRole) {
            return true;
        } else {
            this.router.navigate(['/auth/login']);
            return false;
        }
    }
}
