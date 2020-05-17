import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IYahooUser, YahooUser } from 'app/shared/model/yahoo-user.model';
import { YahooUserService } from './yahoo-user.service';
import { YahooUserComponent } from './yahoo-user.component';
import { YahooUserDetailComponent } from './yahoo-user-detail.component';
import { YahooUserUpdateComponent } from './yahoo-user-update.component';

@Injectable({ providedIn: 'root' })
export class YahooUserResolve implements Resolve<IYahooUser> {
  constructor(private service: YahooUserService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IYahooUser> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((yahooUser: HttpResponse<YahooUser>) => {
          if (yahooUser.body) {
            return of(yahooUser.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new YahooUser());
  }
}

export const yahooUserRoute: Routes = [
  {
    path: '',
    component: YahooUserComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'YahooUsers'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: YahooUserDetailComponent,
    resolve: {
      yahooUser: YahooUserResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'YahooUsers'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: YahooUserUpdateComponent,
    resolve: {
      yahooUser: YahooUserResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'YahooUsers'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: YahooUserUpdateComponent,
    resolve: {
      yahooUser: YahooUserResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'YahooUsers'
    },
    canActivate: [UserRouteAccessService]
  }
];
