import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {AccountService} from '../../service/account.service';
import {Observable} from 'rxjs';

@Component({
  selector: 'app-transfer',
  templateUrl: './transfer.component.html',
  styleUrls: ['./transfer.component.css']
})
export class TransferComponent implements OnInit {

  account$: Observable<Account> = this.accountService.getAccount(this.route.snapshot.paramMap.get('accountNumber'));
  targetNumber: string;
  moneyToTransfer: number;

  constructor(private route: ActivatedRoute, private accountService: AccountService, private router: Router) {
  }

  ngOnInit() {
  }

  onTransfer() {
    this.accountService.transferMoney(this.route.snapshot.paramMap.get('accountNumber'), this.targetNumber, this.moneyToTransfer).subscribe(
      data => {
        this.router.navigate(['account']);
      }
    );
  }
}
