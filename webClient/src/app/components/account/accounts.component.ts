import {Component, OnInit} from '@angular/core';
import {AccountService} from '../../service/account.service';
import {AccountsDatasourceModule} from '../../module/accounts-datasource/accounts-datasource.module';

@Component({
  selector: 'app-account',
  templateUrl: './accounts.component.html',
  styleUrls: ['./accounts.component.css']
})
export class AccountsComponent implements OnInit {


  displayedColumns: string[] = ['number', 'type', 'owner', 'money', 'dateCreated', 'transfer'];
  dataSource: AccountsDatasourceModule;

  constructor(protected accountService: AccountService) {
  }

  ngOnInit() {
    this.dataSource = new AccountsDatasourceModule(this.accountService);
    this.dataSource.loadClients();
  }
}
