import { Order } from '../model/order';

export interface OrderResponse {
    content: Order[],
    totalElements: number,
    totalPages: number,
    size: number,
    number: number
}
