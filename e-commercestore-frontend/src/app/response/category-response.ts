import { Category } from '../model/category';

export interface CategoryResponse {
    content: Category[],
    totalElements: number,
    totalPages: number,
    size: number,
    number: number
}
