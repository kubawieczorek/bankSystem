import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {BehaviorSubject, Observable, of} from 'rxjs';
import {Client} from '../../model/client';
import {ClientService} from '../../service/client.service';
import {CollectionViewer} from '@angular/cdk/collections';
import {catchError, finalize} from 'rxjs/operators';
import {AccountService} from '../../service/account.service';



@NgModule({
  declarations: [],
  imports: [
    CommonModule
  ]
})
export class AccountsDatasourceModule {
  private accountsSubject = new BehaviorSubject<Account[]>([]);
  private loadingSubject = new BehaviorSubject<boolean>(false);

  public loading$ = this.accountsSubject.asObservable();

  constructor(private accountService: AccountService) {
  }

  connect(collectionViewer: CollectionViewer): Observable<Account[]> {
    return this.accountsSubject.asObservable();
  }

  disconnect(collectionViewer: CollectionViewer): void {
    this.accountsSubject.complete();
    this.loadingSubject.complete();
  }

  loadClients() {
    this.loadingSubject.next(true);
    this.accountService.getAccounts().pipe(
      catchError(() => of([])),
      finalize(() => this.loadingSubject.next(false))
    )
      .subscribe(clients => this.accountsSubject.next(clients));
  }
}
