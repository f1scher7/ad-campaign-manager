import {RoleEnum} from "../enums/role.enum";

export interface UserModel{
    id: number;
    username: string;
    password: string;
    email: string;
    role: RoleEnum;
}
