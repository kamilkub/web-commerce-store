import { User } from '../model/user';

export interface UserResponse {
    content: User[],
    totalElements: number,
    totalPages: number,
    size: number,
    number: number
}
