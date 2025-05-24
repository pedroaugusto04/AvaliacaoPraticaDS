import { Component, OnInit } from '@angular/core';
import { NavBarComponent } from '../nav-bar/nav-bar.component';
import {MatTableModule} from '@angular/material/table';
import { Router } from '@angular/router';
import { AccountService } from '../../services/account/account.service';

@Component({
  selector: 'app-contas',
  imports: [NavBarComponent, MatTableModule],
  templateUrl: './contas.component.html',
  styleUrl: './contas.component.scss'
})


export class ContasComponent implements OnInit{

  constructor(private router: Router, private accountService: AccountService) {}
  
  TABLE_DATA: any[] = [
    {numeroConta: 'M13-2ZSDA', saldo: 1000},
    {numeroConta: 'M13-2ZSDA', saldo: 1000},
    {numeroConta: 'M13-2ZSDA', saldo: 1000},
    {numeroConta: 'M13-2ZSDA', saldo: 1000},
    {numeroConta: 'M13-2ZSDA', saldo: 1000},
    {numeroConta: 'M13-2ZSDA', saldo: 1000},
  ];

  displayedColumns: string[] = ['numeroConta','saldo'];
  dataSource = this.TABLE_DATA;

  ngOnInit(): void {
      this.accountService.getAccounts().subscribe({
        next:(accounts) => {
          console.log(accounts);
        },
        error: () => {

        }
      })
  }

  goToRegister() {
    this.router.navigate(['/cliente'])
  }
}
