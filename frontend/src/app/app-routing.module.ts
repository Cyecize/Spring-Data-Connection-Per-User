import {NgModule} from '@angular/core';
import {PreloadAllModules, RouterModule, Routes} from '@angular/router';
import {AppRoutingPath} from "./app-routing.path";

const routes: Routes = [
  {
    path: AppRoutingPath.DATABASE_CONNECT.path,
    loadChildren: () => import('./pages/database-connect/database-connect.module').then(m => m.DatabaseConnectModule),
  },
  {
    path: AppRoutingPath.DATABASE_SELECT.path,
    loadChildren: () => import('./pages/database-select/database-select.module').then(m => m.DatabaseSelectModule),
  },
  {
    path: AppRoutingPath.DATABASE_CREATE.path,
    loadChildren: () => import('./pages/database-create/database-create.module').then(m => m.DatabaseCreateModule),
  },
  {
    path: AppRoutingPath.HOME.path,
    loadChildren: () => import('./pages/home/home.module').then(m => m.HomeModule),
    pathMatch: 'full'
  },
  {
    path: '**',
    loadChildren: () => import('./pages/not-found/not-found.module').then(m => m.NotFoundModule)
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes, {preloadingStrategy: PreloadAllModules})],
  exports: [RouterModule]
})
export class AppRoutingModule {

}
