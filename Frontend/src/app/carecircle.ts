import { Task } from "./task";

export interface CareCircle{
  id: number;
  name: string;
  taskList: Task[];
}
