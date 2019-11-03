import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {ClientListComponent} from './components/client-list/client-list.component';
import {LoginComponent} from './components/login/login.component';
import {AccountsComponent} from './components/account/accounts.component';
import {OauthLoginComponent} from './components/oauth-login/oauth-login.component';
import {TransferComponent} from './components/transfer/transfer.component';
import {BasicLoginComponent} from './components/basic-login/basic-login.component';


const routes: Routes = [
  {path: 'clients', component: ClientListComponent},
  {path: 'loginBasic', component: BasicLoginComponent},
  {path: 'loginOauth', component: OauthLoginComponent},
  {path: 'account', component: AccountsComponent},
  {path: ':accountNumber/transfer', component: TransferComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
