import { OrderStatus } from './enums/order-status';
import { Product } from './product';

export class Order {
    id: number;
    buyerFirstName: string;
    buyerSecondName: string;
    buyerEmail: string;
    shippingCountry: string;
    shippingStreet: string;
    shippingCity: string;
    shippingRegion: string;
    shippingPostalCode: string;
    orderStatus: OrderStatus;
    orderProducts: Product[];
}
