import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IWorldIndex, WorldIndex } from 'app/shared/model/world-index.model';
import { WorldIndexService } from './world-index.service';
import { WorldIndexComponent } from './world-index.component';
import { WorldIndexDetailComponent } from './world-index-detail.component';
import { WorldIndexUpdateComponent } from './world-index-update.component';

@Injectable({ providedIn: 'root' })
export class WorldIndexResolve implements Resolve<IWorldIndex> {
  constructor(private service: WorldIndexService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IWorldIndex> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((worldIndex: HttpResponse<WorldIndex>) => {
          if (worldIndex.body) {
            return of(worldIndex.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new WorldIndex());
  }
}

export const worldIndexRoute: Routes = [
  {
    path: '',
    component: WorldIndexComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'WorldIndices'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: WorldIndexDetailComponent,
    resolve: {
      worldIndex: WorldIndexResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'WorldIndices'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: WorldIndexUpdateComponent,
    resolve: {
      worldIndex: WorldIndexResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'WorldIndices'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: WorldIndexUpdateComponent,
    resolve: {
      worldIndex: WorldIndexResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'WorldIndices'
    },
    canActivate: [UserRouteAccessService]
  }
];
