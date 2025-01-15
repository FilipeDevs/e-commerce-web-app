import {Component, inject} from '@angular/core';
import {CommonModule} from '@angular/common';
import {Pagination} from '../../shared/model/request.model';
import {UserProductService} from '../../shared/user/user-product.service';
import {injectQuery} from '@tanstack/angular-query-experimental';
import {lastValueFrom} from 'rxjs';
import {ProductCardComponent} from '../../shop/product-card/product-card.component';

@Component({
  selector: 'app-featured',
  standalone: true,
  imports: [CommonModule, ProductCardComponent],
  templateUrl: './featured.component.html',
  styleUrl: './featured.component.scss',
})
export class FeaturedComponent {
  productService = inject(UserProductService);

  pageRequest: Pagination = {
    page: 0,
    size: 20,
    sort: [],
  };

  featuredProductQuery = injectQuery(() => ({
    queryKey: ['featured-products', this.pageRequest],
    queryFn: () =>
      lastValueFrom(
        this.productService.findAllFeaturedProducts(this.pageRequest)
      ),
  }));
}
