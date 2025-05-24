import { Component, OnInit } from '@angular/core';
import { NavBarComponent } from '../nav-bar/nav-bar.component';
import {MatTableModule} from '@angular/material/table';
import { Router } from '@angular/router';
import { AccountService } from '../../services/account/account.service';
import { ConfirmService } from '../../services/confirm/confirm.service';
import { BehaviorSubject } from 'rxjs';
import { Account } from '../../models/Account';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-contas',
  imports: [NavBarComponent, MatTableModule,CommonModule],
  templateUrl: './contas.component.html',
  styleUrl: './contas.component.scss'
})


export class ContasComponent implements OnInit{

  
  displayedColumns: string[] = ['numeroConta','saldo'];
  dataSource: any;
  accounts$: BehaviorSubject<Account[]> = new BehaviorSubject<Account[]>([]);

  constructor(private router: Router, private accountService: AccountService, private confirmService: ConfirmService) {}

  ngOnInit(): void {
      this.accountService.getUserAccounts().subscribe({
        next:(accounts) => {
          this.accounts$.next(accounts);
          
          this.dataSource = this.accounts$;
        },
        error: (error) => {
          if (error.error && error.error.message) {
            this.confirmService.error(error.error.message,error.error.subMessage ?? "");
            return;
          }
          this.confirmService.error("Erro ao recuperar contas do usuário","");
        }
      })
  }

  createAccount() {
    this.accountService.createAccount().subscribe({
      next:(createdAccount: Account) => {

        const accounts = this.accounts$.getValue();

        accounts.push(createdAccount);

        this.accounts$.next(accounts);
        
        this.confirmService.successAutoClose("Nova conta criada com sucesso!","");

      },
      error:(error) => {
        if (error.error && error.error.message) {
          this.confirmService.error(error.error.message,error.error.subMessage ?? "");
          return;
        }
        this.confirmService.error("Erro ao criar uma nova conta.","");
      }
    });
  }
}
