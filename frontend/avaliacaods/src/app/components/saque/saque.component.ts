import { Component } from '@angular/core';
import { NavBarComponent } from '../nav-bar/nav-bar.component';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { FormControl, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatButtonModule } from '@angular/material/button';
import { AccountService } from '../../services/account/account.service';
import { BehaviorSubject } from 'rxjs';
import { Account } from '../../models/Account';
import { WithDrawalService } from '../../services/withdrawal/withdrawal.service';
import { ConfirmService } from '../../services/confirm/confirm.service';
import { CommonModule } from '@angular/common';
import { Withdrawal } from '../../models/Withdrawal';

@Component({
  selector: 'app-saque',
  imports: [NavBarComponent, MatInputModule, MatSelectModule, MatFormFieldModule, MatButtonModule, ReactiveFormsModule, CommonModule,FormsModule],
  templateUrl: './saque.component.html',
  styleUrl: './saque.component.scss'
})
export class SaqueComponent {

  accounts$: BehaviorSubject<Account[]> = new BehaviorSubject<Account[]>([]);
  selectedAccount: string = "";
  valor = new FormControl('');

  constructor(private accountService: AccountService, private withdrawalService: WithDrawalService, private confirmService: ConfirmService) { }

  ngOnInit(): void {
    this.accountService.getUserAccounts().subscribe({
      next: (accounts) => {
        this.accounts$.next(accounts);
      },
      error: () => {
        this.confirmService.error("Erro ao recuperar contas do usuário", "");
      }
    })
  }

  onWithdrawal() {
    const withdrawal: Withdrawal = {
      numeroConta: this.selectedAccount,
      valor: this.valor.value || "0"
    }

    this.withdrawalService.withdraw(withdrawal).subscribe({
      next:() => {
        this.confirmService.successAutoClose("Saque realizado com sucesso!","");
      },
      error:() => {
        this.confirmService.error("Erro ao sacar","");
      }
    })
  }
}
