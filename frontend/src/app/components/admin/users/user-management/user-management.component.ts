import {Component, OnInit} from '@angular/core';
import {AdminNavbarComponent} from "../../admin-navbar/admin-navbar.component";
import {UserModel} from "../../../../models/user.model";
import {UserService} from "../../../../services/user.service";
import {NgForOf, NgIf} from "@angular/common";
import {UserEditModalComponent} from "../user-edit-modal/user-edit-modal.component";
import {RoleEnum} from "../../../../enums/role.enum";

@Component({
    selector: 'app-user-management',
    standalone: true,
    imports: [
        AdminNavbarComponent,
        NgForOf,
        NgIf,
        UserEditModalComponent
    ],
    templateUrl: './user-management.component.html',
    styleUrl: './user-management.component.css'
})
export class UserManagementComponent implements OnInit {

    users: UserModel[] = [];
    isModalVisible = false;
    selectedUser: UserModel = {} as UserModel;

    constructor(private userService: UserService) {
    }

    ngOnInit() {
        this.loadUsers();
    }

    loadUsers() {
        this.userService.getAllUsers().subscribe(
            users => {
                this.users = users;
            },
            error => {
                console.log('Error fetching users:', error);
            }
        );
    }

    editUser(user: UserModel) {
        this.selectedUser = { ...user };
        this.isModalVisible = true;
    }

    deleteUser(userToDelete: UserModel) {
        if (confirm('Are you sure you want to delete this user?')) {
            this.userService.deleteUser(userToDelete).subscribe(() => {
                this.users = this.users.filter(user => user.id !== userToDelete.id);
            }, error => {
                console.log('Error deleting user:', error);
            });
        }
    }

    showAddUserModal() {
        this.selectedUser = {
            id: 0,
            username: '',
            password: '',
            email: '',
            role: RoleEnum.USER,
        };
        this.isModalVisible = true;
    }

    onModalClose() {
        this.isModalVisible = false;
    }

    onSaveUser(updatedUser: UserModel) {
        if (updatedUser.id === 0) {
            this.userService.createUser(updatedUser).subscribe(newUser => {
                this.users.push(newUser);
                this.isModalVisible = false;
                window.location.reload();
            }, error => {
                console.log('Error creating user:', error);
            });
        } else {
            // Aktualizacja istniejącego użytkownika
            this.userService.updateUser(updatedUser).subscribe(() => {
                this.users = this.users.map(user => user.id === updatedUser.id ? updatedUser : user);
                this.isModalVisible = false;
                window.location.reload();
            }, error => {
                console.log('Error updating user:', error);
            });
        }
    }

}
