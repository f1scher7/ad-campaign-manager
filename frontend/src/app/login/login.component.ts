import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
    selector: 'app-login',
    standalone: true,
    imports: [CommonModule, FormsModule],
    templateUrl: './login.component.html',
    styleUrls: ['./login.component.css']
})
export class LoginComponent {
    username: string = '';
    password: string = '';

    constructor(private http: HttpClient, private router: Router) {}

    onSubmit() {
        this.http.post('http://localhost:8080/auth/login', { username: this.username, password: this.password })
            .subscribe({
                next: (response: any) => {
                    console.log('Login successful', response);
                    this.router.navigate(['/home']);
                },
                error: (err) => {
                    console.error('Login failed', err);
                    alert('Invalid username or password');
                }
            });
    }
}
