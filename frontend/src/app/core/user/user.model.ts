import {RoleModel} from "./role.model";

export interface UserModel {
  id: number;
  username: string;
  dateRegistered: string;
  roles: RoleModel[];
}
