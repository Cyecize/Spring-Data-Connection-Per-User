import {NgModule} from '@angular/core';
import {PreloadAllModules, RouterModule, Routes} from '@angular/router';
import {AppRoutingPath} from "./app-routing.path";
import {IsAuthenticatedGuard} from "./core/guards/is-authenticated.guard";
import {HasConnectionGuard} from "./core/guards/has-connection.guard";
import {HasSelectedDatabaseGuard} from "./core/guards/has-selected-database.guard";

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
    path: AppRoutingPath.LOGIN.path,
    loadChildren: () => import('./pages/login/login.module').then(m => m.LoginModule),
    canActivate: [HasConnectionGuard, HasSelectedDatabaseGuard]
  },
  {
    path: AppRoutingPath.HOME.path,
    loadChildren: () => import('./pages/home/home.module').then(m => m.HomeModule),
    pathMatch: 'full',
    canActivate: [IsAuthenticatedGuard]
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
