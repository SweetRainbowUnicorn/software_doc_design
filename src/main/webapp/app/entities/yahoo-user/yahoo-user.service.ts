import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IYahooUser } from 'app/shared/model/yahoo-user.model';

type EntityResponseType = HttpResponse<IYahooUser>;
type EntityArrayResponseType = HttpResponse<IYahooUser[]>;

@Injectable({ providedIn: 'root' })
export class YahooUserService {
  public resourceUrl = SERVER_API_URL + 'api/yahoo-users';

  constructor(protected http: HttpClient) {}

  create(yahooUser: IYahooUser): Observable<EntityResponseType> {
    return this.http.post<IYahooUser>(this.resourceUrl, yahooUser, { observe: 'response' });
  }

  update(yahooUser: IYahooUser): Observable<EntityResponseType> {
    return this.http.put<IYahooUser>(this.resourceUrl, yahooUser, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IYahooUser>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IYahooUser[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
