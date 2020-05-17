import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ISavedChart } from 'app/shared/model/saved-chart.model';

type EntityResponseType = HttpResponse<ISavedChart>;
type EntityArrayResponseType = HttpResponse<ISavedChart[]>;

@Injectable({ providedIn: 'root' })
export class SavedChartService {
  public resourceUrl = SERVER_API_URL + 'api/saved-charts';

  constructor(protected http: HttpClient) {}

  create(savedChart: ISavedChart): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(savedChart);
    return this.http
      .post<ISavedChart>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(savedChart: ISavedChart): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(savedChart);
    return this.http
      .put<ISavedChart>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ISavedChart>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ISavedChart[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(savedChart: ISavedChart): ISavedChart {
    const copy: ISavedChart = Object.assign({}, savedChart, {
      startDate: savedChart.startDate && savedChart.startDate.isValid() ? savedChart.startDate.toJSON() : undefined,
      endDate: savedChart.endDate && savedChart.endDate.isValid() ? savedChart.endDate.toJSON() : undefined
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.startDate = res.body.startDate ? moment(res.body.startDate) : undefined;
      res.body.endDate = res.body.endDate ? moment(res.body.endDate) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((savedChart: ISavedChart) => {
        savedChart.startDate = savedChart.startDate ? moment(savedChart.startDate) : undefined;
        savedChart.endDate = savedChart.endDate ? moment(savedChart.endDate) : undefined;
      });
    }
    return res;
  }
}
