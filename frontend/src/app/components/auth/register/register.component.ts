import { Component } from '@angular/core';
import { FormsModule } from "@angular/forms";
import { Router, RouterLink } from "@angular/router";
import { NgClass, NgIf } from "@angular/common";
import { HttpClient } from "@angular/common/http";

@Component({
    selector: 'app-register',
    standalone: true,
    imports: [
        FormsModule,
        RouterLink,
        NgIf,
        NgClass
    ],
    templateUrl: './register.component.html',
    styleUrl: './register.component.css'
})
export class RegisterComponent {
    username: string = '';
    email: string = '';
    password: string = '';
    errorMess: string = '';
    successMess: string = '';

    constructor(private http: HttpClient, private router: Router) {}

    onSubmit() {
        this.http.post('http://localhost:8080/api/auth/register', { username: this.username, email: this.email, password: this.password })
            .subscribe({
                next: (response: any) => {
                    this.successMess = 'Registration successful! Redirecting to login...';
                    setTimeout(() => {
                        this.router.navigate(['/auth/login']);
                    }, 2300);
                },
                error: (errorResponse) => {
                    if (errorResponse.error.invalidUsername) {
                        this.errorMess = errorResponse.error.invalidUsername;
                    } else if (errorResponse.error.userAlreadyExists) {
                        this.errorMess = errorResponse.error.userAlreadyExists;
                    } else if (errorResponse.error.invalidPassword) {
                        this.errorMess = errorResponse.error.invalidPassword;
                    } else if (errorResponse.error.invalidEmail) {
                        this.errorMess = errorResponse.error.invalidEmail;
                    } else if (errorResponse.error.emailAlreadyExists) {
                        this.errorMess = errorResponse.error.emailAlreadyExists;
                    } else {
                        this.errorMess = 'An unexpected error occurred';
                    }
                }
            });
    }
}
