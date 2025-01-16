import {Component, effect, inject, OnInit, PLATFORM_ID} from '@angular/core';
import { CartService } from '../cart.service';
import { Oauth2Service } from '../../auth/oauth2.service';
import {ToastService} from '../../shared/toast/toast.service';
import {CartItem, CartItemAdd} from '../cart.model';
import { injectQuery } from '@tanstack/angular-query-experimental';
import {CurrencyPipe, isPlatformBrowser} from '@angular/common';
import {lastValueFrom} from 'rxjs';
import {RouterLink} from '@angular/router';

@Component({
  selector: 'app-cart',
  imports: [
    CurrencyPipe,
    RouterLink
  ],
  templateUrl: './cart.component.html',
  styleUrl: './cart.component.scss'
})
export class CartComponent implements OnInit {
  cartService = inject(CartService);
  oauth2Service = inject(Oauth2Service);
  toastService = inject(ToastService);

  cart: Array<CartItem> = [];

  platformId = inject(PLATFORM_ID);

  labelCheckout = 'Login to checkout';

  action: 'login' | 'checkout' = 'login';

  isInitPaymentSessionLoading = false;

  cartQuery = injectQuery(() => ({
    queryKey: ['cart'],
    queryFn: () => lastValueFrom(this.cartService.getCartDetail()),
  }));


  constructor() {
    this.extractListToUpdate();
    this.checkUserLoggedIn();
  }

  private extractListToUpdate() {
    effect(() => {
      if (this.cartQuery.isSuccess()) {
        this.cart = this.cartQuery.data().products;
      }
    });
  }

  private checkUserLoggedIn() {
    effect(() => {
      const connectedUserQuery = this.oauth2Service.connectedUserQuery;
      if (connectedUserQuery?.isError()) {
        this.labelCheckout = 'Login to checkout';
        this.action = 'login';
      } else if (connectedUserQuery?.isSuccess()) {
        this.labelCheckout = 'Checkout';
        this.action = 'checkout';
      }
    });
  }

  ngOnInit(): void {
    this.cartService.addedToCart.subscribe((cart) => this.updateQuantity(cart));
  }

  private updateQuantity(cartUpdated: Array<CartItemAdd>) {
    for (const cartItemToUpdate of this.cart) {
      const itemToUpdate = cartUpdated.find(
        (item) => item.publicId === cartItemToUpdate.publicId
      );
      if (itemToUpdate) {
        cartItemToUpdate.quantity = itemToUpdate.quantity;
      } else {
        this.cart.splice(this.cart.indexOf(cartItemToUpdate), 1);
      }
    }
  }

  addQuantityToCart(publicId: string) {
    this.cartService.addToCart(publicId, 'add');
  }

  removeQuantityToCart(publicId: string, quantity: number) {
    if (quantity > 1) {
      this.cartService.addToCart(publicId, 'remove');
    }
  }

  removeItem(publicId: string) {
    const itemToRemoveIndex = this.cart.findIndex(
      (item) => item.publicId === publicId
    );
    if (itemToRemoveIndex) {
      this.cart.splice(itemToRemoveIndex, 1);
    }
    this.cartService.removeFromCart(publicId);
  }

  computeTotal() {
    return this.cart.reduce((acc, item) => acc + item.price * item.quantity, 0);
  }

  checkIfEmptyCart(): boolean {
    if (isPlatformBrowser(this.platformId)) {
      return (
        this.cartQuery.isSuccess() &&
        this.cartQuery.data().products.length === 0
      );
    } else {
      return false;
    }
  }

  checkout() {
    if (this.action === 'login') {
      this.oauth2Service.login();
    } else if (this.action === 'checkout') {
      this.isInitPaymentSessionLoading = true;
      const cartItemsAdd = this.cart.map(
        (item) =>
          ({ publicId: item.publicId, quantity: item.quantity } as CartItemAdd)
      );
    }
  }
}


