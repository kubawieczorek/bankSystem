import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AccountService {

  private accountUrl: string;

  constructor(protected http: HttpClient) {
    this.accountUrl = 'http://localhost:8080/accounts';
  }

  public getAccounts(): Observable<Account[]> {
    return this.http.get<Account[]>(this.accountUrl);
  }

  public getAccount(accountNumber: string): Observable<Account> {
    return this.http.get<Account>(this.accountUrl + '/' + accountNumber);
  }

  public transferMoney(accountNumberFrom: string, accountNumberTo: string, moneyToTransfer: number) {
    const data = {money: moneyToTransfer, targetAccountNumber: accountNumberTo};
    return this.http.post(this.accountUrl + '/' + accountNumberFrom + '/transfer', data);
  }
}
