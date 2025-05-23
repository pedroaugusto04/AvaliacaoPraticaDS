import { Routes } from '@angular/router';
import { LoginComponent } from './components/login/login.component';
import { MenuComponent } from './components/menu/menu.component';
import { ContasComponent } from './components/contas/contas.component';

export const routes: Routes = [
    { path: '', redirectTo: '/login', pathMatch: 'full' },
    {path: 'login', component: LoginComponent},
    {path: 'menu',component: MenuComponent},
    {path: 'conta',component: ContasComponent}
];
