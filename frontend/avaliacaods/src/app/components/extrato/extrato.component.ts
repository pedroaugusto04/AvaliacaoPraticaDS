import { Component, OnInit } from '@angular/core';
import { NavBarComponent } from '../nav-bar/nav-bar.component';
import { MatSelectModule } from '@angular/material/select';
import { MatTableModule } from '@angular/material/table';
import { BehaviorSubject, Observable, of } from 'rxjs';
import { Account } from '../../models/Account';
import { Router } from '@angular/router';
import { AccountService } from '../../services/account/account.service';
import { ConfirmService } from '../../services/confirm/confirm.service';
import { CommonModule } from '@angular/common';
import { BankStatement } from '../../models/BankStatement';
import { BankStatementService } from '../../services/bank-statement/bank-statement.service';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-extrato',
  imports: [NavBarComponent, MatSelectModule, MatTableModule, CommonModule, FormsModule],
  templateUrl: './extrato.component.html',
  styleUrl: './extrato.component.scss'
})
export class ExtratoComponent implements OnInit {

  displayedColumns: string[] = ['data','valor','tipo'];
  dataSource: any;

  accounts$: BehaviorSubject<Account[]> = new BehaviorSubject<Account[]>([]);
  bankStatements$: BehaviorSubject<BankStatement[]> = new BehaviorSubject<BankStatement[]>([]);

  selectedAccount: string = "";

  constructor(private router: Router, private accountService: AccountService, private bankStatementService: BankStatementService, private confirmService: ConfirmService) { }

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

  onSearch() {
    if (!this.selectedAccount) {
      this.confirmService.errorAutoClose("Informações inválidas","Por favor, selecione a conta da qual deseja visualizar o extrato");
      return;
    }

    this.bankStatementService.getAccountBankStatements(this.selectedAccount).subscribe({
      next: (bankStatements: BankStatement[]) => {
        this.bankStatements$.next(bankStatements);

        this.dataSource = this.bankStatements$;

        if (bankStatements.length == 0){
          this.confirmService.warningAutoClose("Não foram encontrados extratos para a conta informada","");
        }
      },
      error: () => {
        this.confirmService.error("Erro ao recuperar o extrato da conta " + this.selectedAccount, "");
      }
    });
  }

}
