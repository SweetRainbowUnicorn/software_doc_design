import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IYahooUser } from 'app/shared/model/yahoo-user.model';

@Component({
  selector: 'jhi-yahoo-user-detail',
  templateUrl: './yahoo-user-detail.component.html'
})
export class YahooUserDetailComponent implements OnInit {
  yahooUser: IYahooUser | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ yahooUser }) => (this.yahooUser = yahooUser));
  }

  previousState(): void {
    window.history.back();
  }
}
