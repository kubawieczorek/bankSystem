import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {BehaviorSubject, Observable, of} from 'rxjs';
import {Client} from '../../model/client';
import {ClientService} from '../../service/client.service';
import {CollectionViewer, DataSource} from '@angular/cdk/collections';
import {catchError, finalize} from 'rxjs/operators';


@NgModule({
  declarations: [],
  imports: [
    CommonModule
  ]
})
export class ClientsDatasourceModule implements DataSource<Client> {
  private clientsSubject = new BehaviorSubject<Client[]>([]);
  private loadingSubject = new BehaviorSubject<boolean>(false);

  public loading$ = this.clientsSubject.asObservable();

  constructor(private clientService: ClientService) {
  }

  connect(collectionViewer: CollectionViewer): Observable<Client[]> {
    return this.clientsSubject.asObservable();
  }

  disconnect(collectionViewer: CollectionViewer): void {
    this.clientsSubject.complete();
    this.loadingSubject.complete();
  }

  loadClients() {
    this.loadingSubject.next(true);
    this.clientService.findAll().pipe(
      catchError(() => of([])),
      finalize(() => this.loadingSubject.next(false))
    )
      .subscribe(clients => this.clientsSubject.next(clients));
  }
}
