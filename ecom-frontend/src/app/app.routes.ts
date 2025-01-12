import { Routes } from '@angular/router';
import {AdminCategoriesComponent} from './admin/category/admin-categories/admin-categories.component';
import {CreateCategoryComponent} from './admin/category/create-category/create-category.component';
import {roleCheckGuard} from './auth/role-check.guard';
import {AdminProductsComponent} from './admin/product/admin-products/admin-products.component';
import {CreateProductComponent} from './admin/product/create-product/create-product.component';

export const routes: Routes = [
  {
    path: 'admin/categories/list',
    component: AdminCategoriesComponent,
    canActivate: [roleCheckGuard],
    data: {
      authorities: ['ROLE_ADMIN'],
    },
  },
  {
    path: 'admin/categories/create',
    component: CreateCategoryComponent,
    canActivate: [roleCheckGuard],
    data: {
      authorities: ['ROLE_ADMIN'],
    },
  },
  {
    path: 'admin/products/create',
    component: CreateProductComponent,
    canActivate: [roleCheckGuard],
    data: {
      authorities: ['ROLE_ADMIN'],
    },
  },
  {
    path: 'admin/products/list',
    component: AdminProductsComponent,
    canActivate: [roleCheckGuard],
    data: {
      authorities: ['ROLE_ADMIN'],
    },
  }
];
