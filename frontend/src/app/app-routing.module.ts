import {NgModule} from '@angular/core';
import {PreloadAllModules, RouterModule, Routes} from '@angular/router';
import {AppRoutingPath} from "./app-routing.path";

const routes: Routes = [
  {
    path: AppRoutingPath.DATABASE_CONNECT.path,
    loadChildren: () => import('./pages/database-connect/database-connect.module').then(m => m.DatabaseConnectModule),
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
