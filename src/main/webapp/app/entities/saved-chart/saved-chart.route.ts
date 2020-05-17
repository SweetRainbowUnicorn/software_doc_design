import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ISavedChart, SavedChart } from 'app/shared/model/saved-chart.model';
import { SavedChartService } from './saved-chart.service';
import { SavedChartComponent } from './saved-chart.component';
import { SavedChartDetailComponent } from './saved-chart-detail.component';
import { SavedChartUpdateComponent } from './saved-chart-update.component';

@Injectable({ providedIn: 'root' })
export class SavedChartResolve implements Resolve<ISavedChart> {
  constructor(private service: SavedChartService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ISavedChart> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((savedChart: HttpResponse<SavedChart>) => {
          if (savedChart.body) {
            return of(savedChart.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new SavedChart());
  }
}

export const savedChartRoute: Routes = [
  {
    path: '',
    component: SavedChartComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'SavedCharts'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: SavedChartDetailComponent,
    resolve: {
      savedChart: SavedChartResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'SavedCharts'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: SavedChartUpdateComponent,
    resolve: {
      savedChart: SavedChartResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'SavedCharts'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: SavedChartUpdateComponent,
    resolve: {
      savedChart: SavedChartResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'SavedCharts'
    },
    canActivate: [UserRouteAccessService]
  }
];
