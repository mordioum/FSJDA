import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { Ligue } from './ligue.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Ligue>;

@Injectable()
export class LigueService {

    private resourceUrl =  SERVER_API_URL + 'api/ligues';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) { }

    create(ligue: Ligue): Observable<EntityResponseType> {
        const copy = this.convert(ligue);
        return this.http.post<Ligue>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(ligue: Ligue): Observable<EntityResponseType> {
        const copy = this.convert(ligue);
        return this.http.put<Ligue>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Ligue>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Ligue[]>> {
        const options = createRequestOption(req);
        return this.http.get<Ligue[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Ligue[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Ligue = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Ligue[]>): HttpResponse<Ligue[]> {
        const jsonResponse: Ligue[] = res.body;
        const body: Ligue[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Ligue.
     */
    private convertItemFromServer(ligue: Ligue): Ligue {
        const copy: Ligue = Object.assign({}, ligue);
        copy.dateCreation = this.dateUtils
            .convertLocalDateFromServer(ligue.dateCreation);
        return copy;
    }

    /**
     * Convert a Ligue to a JSON which can be sent to the server.
     */
    private convert(ligue: Ligue): Ligue {
        const copy: Ligue = Object.assign({}, ligue);
        copy.dateCreation = this.dateUtils
            .convertLocalDateToServer(ligue.dateCreation);
        return copy;
    }
}
