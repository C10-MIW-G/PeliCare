
export interface NewTask {
    // task id will be decided by backend
    date: Date;
    title: string;
    description: string; 
    careCircleId: number;
}