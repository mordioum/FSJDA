import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { DojoClub } from './dojo-club.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<DojoClub>;

@Injectable()
export class DojoClubService {

    private resourceUrl =  SERVER_API_URL + 'api/dojo-clubs';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) { }

    create(dojoClub: DojoClub): Observable<EntityResponseType> {
        const copy = this.convert(dojoClub);
        return this.http.post<DojoClub>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(dojoClub: DojoClub): Observable<EntityResponseType> {
        const copy = this.convert(dojoClub);
        return this.http.put<DojoClub>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<DojoClub>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<DojoClub[]>> {
        const options = createRequestOption(req);
        return this.http.get<DojoClub[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<DojoClub[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: DojoClub = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<DojoClub[]>): HttpResponse<DojoClub[]> {
        const jsonResponse: DojoClub[] = res.body;
        const body: DojoClub[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to DojoClub.
     */
    private convertItemFromServer(dojoClub: DojoClub): DojoClub {
        const copy: DojoClub = Object.assign({}, dojoClub);
        copy.dateCreation = this.dateUtils
            .convertLocalDateFromServer(dojoClub.dateCreation);
        return copy;
    }

    /**
     * Convert a DojoClub to a JSON which can be sent to the server.
     */
    private convert(dojoClub: DojoClub): DojoClub {
        const copy: DojoClub = Object.assign({}, dojoClub);
        copy.dateCreation = this.dateUtils
            .convertLocalDateToServer(dojoClub.dateCreation);
        return copy;
    }
}
