import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { map, Observable } from 'rxjs';
import { UserModel } from "../models/user.model";
import { RoleEnum } from "../enums/role.enum";


@Injectable({
    providedIn: 'root'
})
export class UserService {

    baseUrl: string = 'http://localhost:8080/api/users';
    constructor(private http: HttpClient) { }

    getAllUsers(): Observable<UserModel[]> {
        return this.http.get<UserModel[]>(this.baseUrl + '/all').pipe(
            map(users => {
                return users.map(user => ({
                    id: user.id,
                    username: user.username,
                    password: user.password,
                    email: user.email,
                    role: this.mapRole(user.role),
                }));
            })
        );
    }

    createUser(user: UserModel): Observable<UserModel> {
        return this.http.post<UserModel>(`${this.baseUrl}/create-user`, user, {
            headers: { 'Content-Type': 'application/json' }
        });
    }

    updateUser(user: UserModel): Observable<UserModel> {
        return this.http.put<UserModel>(`${this.baseUrl}/update-user-by-id/${user.id}`, user);
    }

    deleteUser(user: UserModel): Observable<void> {
        return this.http.delete<void>(`${this.baseUrl}/delete-user-by-id/${user.id}`);
    }

    private mapRole(role: string): RoleEnum {
        switch (role) {
            case 'USER':
                return RoleEnum.USER;
            case 'ADMIN':
                return RoleEnum.ADMIN;
            default:
                return RoleEnum.USER;
        }
    }
}
