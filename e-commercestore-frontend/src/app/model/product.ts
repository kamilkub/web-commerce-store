import { Category } from './category';

export class Product {

     id: number;
     unitsInStock: number;
     name: string;
     price: number;
     description: string;
     imageUrl: string;
     isActive: boolean;
     category: Category;
}
