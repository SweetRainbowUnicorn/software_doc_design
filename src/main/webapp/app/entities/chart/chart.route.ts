import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IChart, Chart } from 'app/shared/model/chart.model';
import { ChartService } from './chart.service';
import { ChartComponent } from './chart.component';
import { ChartDetailComponent } from './chart-detail.component';
import { ChartUpdateComponent } from './chart-update.component';

@Injectable({ providedIn: 'root' })
export class ChartResolve implements Resolve<IChart> {
  constructor(private service: ChartService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IChart> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((chart: HttpResponse<Chart>) => {
          if (chart.body) {
            return of(chart.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Chart());
  }
}

export const chartRoute: Routes = [
  {
    path: '',
    component: ChartComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Charts'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ChartDetailComponent,
    resolve: {
      chart: ChartResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Charts'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ChartUpdateComponent,
    resolve: {
      chart: ChartResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Charts'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ChartUpdateComponent,
    resolve: {
      chart: ChartResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Charts'
    },
    canActivate: [UserRouteAccessService]
  }
];
