import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AddProductComponent } from './components/add-product/add-product.component';
import { AdminPanelComponent } from './components/admin-panel/admin-panel.component';
import { CategoriesComponent } from './components/admin-panel/categories/categories.component';
import { EditOrderComponent } from './components/admin-panel/orders/edit-order/edit-order.component';
import { OrdersComponent } from './components/admin-panel/orders/orders.component';
import { ProductsComponent } from './components/admin-panel/products/products.component';
import { UsersComponent } from './components/admin-panel/users/users.component';
import { CartComponent } from './components/cart/cart.component';
import { CheckoutComponent } from './components/checkout/checkout.component';
import { ConfirmTokenComponent } from './components/confirm-token/confirm-token.component';
import { EditProductComponent } from './components/edit-product/edit-product.component';
import { ForgotPasswordComponent } from './components/forgot-password/forgot-password.component';
import { HomeComponent } from './components/home/home.component';
import { LoginComponent } from './components/login/login.component';
import { NotfoundComponent } from './components/notfound/notfound.component';
import { OrderTrackingComponent } from './components/order-tracking/order-tracking.component';
import { PaymentFailureComponent } from './components/payment-failure/payment-failure.component';
import { ProductInfoComponent } from './components/product-info/product-info.component';
import { SignUpComponent } from './components/sign-up/sign-up.component';
import { AuthorizationGuardianService } from './service/security/authorization-guardian.service';


const routes: Routes = [
    {path: 'login', component: LoginComponent},
    {path: 'sign-up', component: SignUpComponent},
    {path: '', component: HomeComponent, pathMatch: "full"},
    {path: 'category/:categoryName', component: HomeComponent},
    {path: 'search/:keyword', component: HomeComponent},
    {path: 'product-info/:id', component: ProductInfoComponent},
    {path: 'checkout-section', component: CheckoutComponent},
    {path: 'cart-section', component: CartComponent, canActivate: [AuthorizationGuardianService]},
    {path: 'confirm/:token', component: ConfirmTokenComponent},
    {path: 'forgot-password/:token', component: ForgotPasswordComponent},
    {path: 'payment-failure', component: PaymentFailureComponent},
    {path: 'admin-panel/users', component: UsersComponent, canActivate: [AuthorizationGuardianService]},
    {path: 'admin-panel/users/:keyword', component: UsersComponent, canActivate: [AuthorizationGuardianService]},
    {path: 'admin-panel/products', component: ProductsComponent, canActivate: [AuthorizationGuardianService]},
    {path: 'admin-panel/products/:keyword', component: ProductsComponent, canActivate: [AuthorizationGuardianService]},
    {path: 'admin-panel/categories', component: CategoriesComponent, canActivate: [AuthorizationGuardianService]},
    {path: 'admin-panel/categories/:keyword', component: CategoriesComponent, canActivate: [AuthorizationGuardianService]},
    {path: 'admin-panel/orders', component: OrdersComponent, canActivate: [AuthorizationGuardianService]},
    {path: 'admin-panel/orders/:keyword', component: OrdersComponent, canActivate: [AuthorizationGuardianService]},
    {path: 'admin-panel', component: AdminPanelComponent, canActivate: [AuthorizationGuardianService]},
    {path: 'order-tracking', component: OrderTrackingComponent, canActivate: [AuthorizationGuardianService]},
    {path: 'order-tracking/:orderProcess', component: OrderTrackingComponent, canActivate: [AuthorizationGuardianService]},
    {path: 'admin-panel/orders/editOrder/:id', component: EditOrderComponent, canActivate: [AuthorizationGuardianService] },
    {path: 'admin-panel/editProduct/:id', component: EditProductComponent, canActivate: [AuthorizationGuardianService]},
    {path: 'admin-panel/addProduct', component: AddProductComponent},
    {path: 'pageNotFound', component: NotfoundComponent},
    {path: '**', redirectTo: '/pageNotFound'}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
