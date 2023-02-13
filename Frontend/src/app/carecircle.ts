import { Task } from "./task";
import { User } from "./user";

export interface CareCircle{
  id: number;
  name: string;
  taskList: Task[];
  userList: User[];
}
