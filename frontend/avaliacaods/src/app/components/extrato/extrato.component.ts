import { Component } from '@angular/core';
import { NavBarComponent } from '../nav-bar/nav-bar.component';
import { MatSelectModule } from '@angular/material/select';
import { MatTableModule } from '@angular/material/table';

@Component({
  selector: 'app-extrato',
  imports: [NavBarComponent, MatSelectModule, MatTableModule],
  templateUrl: './extrato.component.html',
  styleUrl: './extrato.component.scss'
})
export class ExtratoComponent {
  foods: any[] = [
    { value: 'steak-0', viewValue: 'Steak' },
    { value: 'pizza-1', viewValue: 'Pizza' },
    { value: 'tacos-2', viewValue: 'Tacos' },
  ];

  TABLE_DATA: any[] = [
    { data: '13/02/2004', valor: 1000, tipo: 'Cŕedito' },
    { data: '13/02/2004', valor: 1000, tipo: 'Cŕedito' },
    { data: '13/02/2004', valor: 1000, tipo: 'Cŕedito' },
    { data: '13/02/2004', valor: 1000, tipo: 'Cŕedito' }
  ];

  displayedColumns: string[] = ['data','valor','tipo'];
  dataSource = this.TABLE_DATA;

}
