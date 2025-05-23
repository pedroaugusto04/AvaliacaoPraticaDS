import { Component } from '@angular/core';
import { NavBarComponent } from '../nav-bar/nav-bar.component';
import {MatTableModule} from '@angular/material/table';

@Component({
  selector: 'app-contas',
  imports: [NavBarComponent, MatTableModule],
  templateUrl: './contas.component.html',
  styleUrl: './contas.component.scss'
})


export class ContasComponent {
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
}
