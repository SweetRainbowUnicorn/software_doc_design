import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IChart } from 'app/shared/model/chart.model';

type EntityResponseType = HttpResponse<IChart>;
type EntityArrayResponseType = HttpResponse<IChart[]>;

@Injectable({ providedIn: 'root' })
export class ChartService {
  public resourceUrl = SERVER_API_URL + 'api/charts';

  constructor(protected http: HttpClient) {}

  create(chart: IChart): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(chart);
    return this.http
      .post<IChart>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(chart: IChart): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(chart);
    return this.http
      .put<IChart>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IChart>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IChart[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(chart: IChart): IChart {
    const copy: IChart = Object.assign({}, chart, {
      startDate: chart.startDate && chart.startDate.isValid() ? chart.startDate.toJSON() : undefined,
      endDate: chart.endDate && chart.endDate.isValid() ? chart.endDate.toJSON() : undefined
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
      res.body.forEach((chart: IChart) => {
        chart.startDate = chart.startDate ? moment(chart.startDate) : undefined;
        chart.endDate = chart.endDate ? moment(chart.endDate) : undefined;
      });
    }
    return res;
  }
}
