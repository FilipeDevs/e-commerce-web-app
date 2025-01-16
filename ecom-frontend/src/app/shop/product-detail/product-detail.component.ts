import {Component, effect, inject} from '@angular/core';
import {injectParams} from 'ngxtension/inject-params';
import {UserProductService} from '../../shared/service/user/user-product.service';
import {ToastService} from '../../shared/toast/toast.service';
import {Router} from '@angular/router';
import {Pagination} from '../../shared/model/request.model';
import {injectQuery} from '@tanstack/angular-query-experimental';
import {interval, lastValueFrom, take} from 'rxjs';
import {ProductCardComponent} from '../product-card/product-card.component';
import {CurrencyPipe, NgStyle} from '@angular/common';
import {FaIconComponent} from '@fortawesome/angular-fontawesome';
import {CartService} from '../cart.service';
import {Product} from '../../admin/model/product.model';

@Component({
  selector: 'app-product-detail',
  imports: [
    ProductCardComponent,
    NgStyle,
    CurrencyPipe,
    FaIconComponent
  ],
  templateUrl: './product-detail.component.html',
  styleUrl: './product-detail.component.scss'
})
export class ProductDetailComponent {
  publicId = injectParams('publicId');

  productService = inject(UserProductService);
  router = inject(Router);
  toastService = inject(ToastService);
  cartService = inject(CartService);

  lastPublicId = '';

  pageRequest: Pagination = {
    page: 0,
    size: 20,
    sort: [],
  };

  labelAddToCart = 'Add to cart';
  iconAddToCart = 'shopping-cart';

  constructor() {
    effect(() => this.handlePublicIdChange());
    effect(() => this.handleProductQueryError());
    effect(() => this.handleRelatedProductQueryError());
  }

  productQuery = injectQuery(() => ({
    queryKey: ['product', this.publicId()],
    queryFn: () =>
      lastValueFrom(this.productService.findOneByPublicId(this.publicId()!)),
  }));

  relatedProductQuery = injectQuery(() => ({
    queryKey: ['related-product', this.publicId(), this.pageRequest],
    queryFn: () =>
      lastValueFrom(
        this.productService.findRelatedProduct(
          this.pageRequest,
          this.publicId()!
        )
      ),
  }));

  private handlePublicIdChange() {
    if (this.publicId()) {
      if (this.lastPublicId != this.publicId() && this.lastPublicId !== '') {
        this.relatedProductQuery.refetch();
        this.productQuery.refetch();
      }
      this.lastPublicId = this.publicId()!;
    }
  }

  private handleRelatedProductQueryError() {
    if (this.relatedProductQuery.isError()) {
      this.toastService.show(
        'Error! Failed to load related product. please try again.',
        'ERROR'
      );
    }
  }

  private handleProductQueryError() {
    if (this.productQuery.isError()) {
      this.toastService.show(
        'Error! Failed to load product. please try again.',
        'ERROR'
      );
    }
  }

  addToCart(productToAdd: Product) {
    this.cartService.addToCart(productToAdd.publicId, 'add');
    this.labelAddToCart = 'Added to cart';
    this.iconAddToCart = 'check';

    interval(1500)
      .pipe(take(1))
      .subscribe(() => {
        this.labelAddToCart = 'Add to cart';
        this.iconAddToCart = 'shopping-cart';
      });
  }

}
