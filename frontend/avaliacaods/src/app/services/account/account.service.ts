import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { LoginUser } from '../../models/LoginUser';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { Account } from '../../models/Account';

@Injectable({
  providedIn: 'root'
})
export class AccountService {

  private headers = new HttpHeaders().set('Content-Type', 'application/json; charset=utf-8');

  constructor(private httpClient: HttpClient) { }


   getAccounts(): Observable<Account[]> {
      const getUserAccountsUrl = new URL(environment.apiGetUserAccountsUrl, environment.baseUrl).toString();
  
      return this.httpClient.get<Account[]>(getUserAccountsUrl, {headers:this.headers});
    }
}
