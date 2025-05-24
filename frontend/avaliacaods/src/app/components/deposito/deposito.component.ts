import { Component, OnInit } from '@angular/core';
import { NavBarComponent } from '../../components/nav-bar/nav-bar.component';
import { FormControl, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatButtonModule } from '@angular/material/button';
import { BehaviorSubject } from 'rxjs';
import { Account } from '../../models/Account';
import { ConfirmService } from '../../services/confirm/confirm.service';
import { CommonModule } from '@angular/common';
import { Deposit } from '../../models/Deposit';
import { DepositService } from '../../services/deposit/deposit.service';
import { AccountService } from '../../services/account/account.service';

@Component({
  selector: 'app-deposito',
  imports: [NavBarComponent,MatInputModule, MatSelectModule,MatFormFieldModule,MatButtonModule, ReactiveFormsModule,
    CommonModule, MatSelectModule, FormsModule
  ],
  templateUrl: './deposito.component.html',
  styleUrl: './deposito.component.scss'
})
export class DepositoComponent implements OnInit{


  accounts$: BehaviorSubject<Account[]> = new BehaviorSubject<Account[]>([]);
  
  valor = new FormControl("");
  selectedAccount: string = "";

  constructor(private accountService: AccountService, private depositService: DepositService, private confirmService:ConfirmService) {}
  
  ngOnInit(): void {
    this.accountService.getUserAccounts().subscribe({
      next:(accounts) => {
        this.accounts$.next(accounts);
      },
      error: () => {
        this.confirmService.error("Erro ao recuperar contas do usuário","");
      }
    })
  }

  onDeposit() {
    const deposit: Deposit = {
      numeroConta: this.selectedAccount,
      valor: this.valor.value ?? "0"
    }

    this.depositService.deposit(deposit).subscribe({
      next:() => {
        this.confirmService.successAutoClose("Depósito realizado com sucesso!","");
      },
      error: () => {
        this.confirmService.errorAutoClose("Erro ao depositar","");
      }
    })
  }
}
