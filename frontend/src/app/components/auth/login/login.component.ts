import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import {Router, RouterLink} from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
    selector: 'app-login',
    standalone: true,
    imports: [CommonModule, FormsModule, RouterLink],
    templateUrl: './login.component.html',
    styleUrls: ['./login.component.css']
})
export class LoginComponent {
    username: string = '';
    password: string = '';
    errorMess: string = '';

    constructor(private http: HttpClient, private router: Router) {}

    onSubmit() {
        this.http.post('http://localhost:8080/api/auth/login', { username: this.username, password: this.password })
            .subscribe({
                next: (response: any) => {
                    localStorage.setItem('role', response.role);
                    localStorage.setItem('jwtToken', response.jwtToken);

                    console.log(response.role);
                    console.log(response.jwtToken);

                    if (response.role == 'ADMIN') {
                        this.router.navigate(['/adashboard']);
                    } else if (response.role == 'USER') {
                        this.router.navigate(['/udashboard']);
                    }

                },
                error: (errorResponse) => {
                    if (errorResponse.error.loginError) {
                        this.errorMess = errorResponse.error.loginError;
                    } else {
                        this.errorMess = 'An unexpected error occurred';
                    }
                }
            });
    }
}
