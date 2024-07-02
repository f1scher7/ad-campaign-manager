import { Component } from '@angular/core';
import { RouterLink } from "@angular/router";
import {HttpClient} from "@angular/common/http";
import {AdminNavbarComponent} from "../admin-navbar/admin-navbar.component";

@Component({
    selector: 'app-admin-dashboard',
    standalone: true,
    imports: [
        RouterLink,
        AdminNavbarComponent
    ],
    templateUrl: './admin-dashboard.component.html',
    styleUrl: './admin-dashboard.component.css'
})
export class AdminDashboardComponent {
    userCount: number = 0;
    campaignCount: number = 0;

    constructor(private http: HttpClient) {}

    ngOnInit() {
        this.fetchUserCount();
        this.fetchCampaignCount();
    }

    fetchUserCount() {
        this.http.get<number>('http://localhost:8080/api/users/count').subscribe(
            (data) => {
                this.userCount = data;
            },
            (error) => {
                console.error('Error fetching user count', error);
            }
        )
    }

    fetchCampaignCount() {
        this.http.get<number>('http://localhost:8080/api/campaigns/count').subscribe(
            (data) => {
                this.campaignCount = data;
            },
            (error) => {
                console.error('Error fetching campaign count', error);
            }
        )
    }

}
