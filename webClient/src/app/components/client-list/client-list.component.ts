import {Component, OnInit} from '@angular/core';
import {Client} from '../../model/client';
import {ClientService} from '../../service/client.service';
import {Observable} from 'rxjs';
import {ClientsDatasourceModule} from '../../module/clients-datasource/clients-datasource.module';

@Component({
  selector: 'app-client-list',
  templateUrl: './client-list.component.html',
  styleUrls: ['./client-list.component.css']
})
export class ClientListComponent implements OnInit {

  displayedColumns: string[] = ['name', 'email'];
  dataSource: ClientsDatasourceModule;

  constructor(protected userService: ClientService) {
  }

  ngOnInit() {
    this.dataSource = new ClientsDatasourceModule(this.userService);
    this.dataSource.loadClients();
  }

}
