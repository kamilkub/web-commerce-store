import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';


import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './components/login/login.component';
import { SignUpComponent } from './components/sign-up/sign-up.component';
import { HomeComponent } from './components/home/home.component';
import { CheckoutComponent } from './components/checkout/checkout.component';
import { ProductListComponent } from './components/product-list/product-list.component';
import { SidebarComponent } from './components/sidebar/sidebar.component';
import { CartStatusComponent } from './components/cart-status/cart-status.component';
import { ProductInfoComponent } from './components/product-info/product-info.component';
import { CartComponent } from './components/cart/cart.component';
import { SearchInputComponent } from './components/search-input/search-input.component';
import { ErrorResponseInterceptorService } from './service/security/interceptors/error-response-interceptor.service';
import { NotfoundComponent } from './components/notfound/notfound.component';
import { ConfirmTokenComponent } from './components/confirm-token/confirm-token.component';
import { ForgotPasswordComponent } from './components/forgot-password/forgot-password.component';
import { FooterComponent } from './components/footer/footer.component';
import { AdminPanelComponent } from './components/admin-panel/admin-panel.component';
import { HeaderBarComponent } from './components/header-bar/header-bar.component';
import { AddProductComponent } from './components/add-product/add-product.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { EditProductComponent } from './components/edit-product/edit-product.component';
import { PaymentFailureComponent } from './components/payment-failure/payment-failure.component';
import { MatChipsModule, MatIconModule,  MatInputModule, MatSelectModule } from '@angular/material';
import { MatButtonModule } from '@angular/material/button';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { ProductsComponent } from './components/admin-panel/products/products.component';
import { CategoriesComponent } from './components/admin-panel/categories/categories.component';
import { UsersComponent } from './components/admin-panel/users/users.component';
import { OrdersComponent } from './components/admin-panel/orders/orders.component';
import { EditOrderComponent } from './components/admin-panel/orders/edit-order/edit-order.component';
import { OrderTrackingComponent } from './components/order-tracking/order-tracking.component'
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { BurgerMenuComponent } from './components/burger-menu/burger-menu.component';
import { ResizeComponent } from './components/resize/resize.component';


@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    SignUpComponent,
    HomeComponent,
    CheckoutComponent,
    ProductListComponent,
    SidebarComponent,
    CartStatusComponent,
    ProductInfoComponent,
    CartComponent,
    SearchInputComponent,
    NotfoundComponent,
    ConfirmTokenComponent,
    ForgotPasswordComponent,
    FooterComponent,
    AdminPanelComponent,
    HeaderBarComponent,
    AddProductComponent,
    EditProductComponent,
    PaymentFailureComponent,
    ProductsComponent,
    CategoriesComponent,
    UsersComponent,
    OrdersComponent,
    EditOrderComponent,
    OrderTrackingComponent,
    BurgerMenuComponent,
    ResizeComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
    MatInputModule,
    MatSelectModule,
    MatChipsModule,
    MatButtonModule,
    BrowserAnimationsModule,
    MatIconModule,
    NgbModule
  ],
  providers: [
      {provide: HTTP_INTERCEPTORS, useClass: ErrorResponseInterceptorService, multi: true},
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
