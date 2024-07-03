import {Component, EventEmitter, Input, Output} from '@angular/core';
import {FormsModule} from "@angular/forms";
import {NgClass, NgIf, NgStyle} from "@angular/common";
import {UserModel} from "../../../../models/user.model";
import {RoleEnum} from "../../../../enums/role.enum";

@Component({
  selector: 'app-user-edit-modal',
  standalone: true,
    imports: [
        FormsModule,
        NgClass,
        NgStyle,
        NgIf
    ],
  templateUrl: './user-edit-modal.component.html',
  styleUrl: './user-edit-modal.component.css'
})
export class UserEditModalComponent {

    protected readonly RoleEnum = RoleEnum;

    @Input() isVisible = false;
    @Input() user: UserModel = { id: 0, username: '', password: '',  email: '', role: RoleEnum.USER };
    @Output() closeModal = new EventEmitter<void>();
    @Output() saveUser = new EventEmitter<UserModel>();

    close() {
        this.isVisible = false;
        this.closeModal.emit();
    }

    saveNewUser() {
        this.saveUser.emit(this.user);
    }

}
