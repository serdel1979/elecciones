import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from "@angular/common/http";


@Injectable({
    providedIn: 'root'
  })

export class ResultService {

    constructor(private httpClient: HttpClient) {}

    getResults() {
      //http://localhost:8080/12354854985/client/getResults
      return this.httpClient.get<any>(window.origin+window.location.pathname.substring(0,window.location.pathname.substring(1).indexOf("/")+2)+"client/getResults");
      //return this.httpClient.get<any>("http://localhost:8080/12354854985/client/getResults");
    }

}
