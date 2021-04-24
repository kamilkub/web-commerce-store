import { Product } from '../model/product';

export interface ProductResponse {

    content: Product[],
    totalElements: number,
    totalPages: number,
    size: number,
    number: number
}
