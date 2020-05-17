import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IWorldIndex } from 'app/shared/model/world-index.model';

type EntityResponseType = HttpResponse<IWorldIndex>;
type EntityArrayResponseType = HttpResponse<IWorldIndex[]>;

@Injectable({ providedIn: 'root' })
export class WorldIndexService {
  public resourceUrl = SERVER_API_URL + 'api/world-indices';

  constructor(protected http: HttpClient) {}

  create(worldIndex: IWorldIndex): Observable<EntityResponseType> {
    return this.http.post<IWorldIndex>(this.resourceUrl, worldIndex, { observe: 'response' });
  }

  update(worldIndex: IWorldIndex): Observable<EntityResponseType> {
    return this.http.put<IWorldIndex>(this.resourceUrl, worldIndex, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IWorldIndex>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IWorldIndex[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
