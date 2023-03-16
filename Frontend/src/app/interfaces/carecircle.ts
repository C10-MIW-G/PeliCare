import { Task } from "./task";

export interface CareCircle{
  id: number;
  name: string;
  imagefilename: string;
  taskList: Task[];
  imageUrl: string | ArrayBuffer | null
}
