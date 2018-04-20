import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { Saison } from './saison.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Saison>;

@Injectable()
export class SaisonService {

    private resourceUrl =  SERVER_API_URL + 'api/saisons';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) { }

    create(saison: Saison): Observable<EntityResponseType> {
        const copy = this.convert(saison);
        return this.http.post<Saison>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(saison: Saison): Observable<EntityResponseType> {
        const copy = this.convert(saison);
        return this.http.put<Saison>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Saison>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Saison[]>> {
        const options = createRequestOption(req);
        return this.http.get<Saison[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Saison[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Saison = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Saison[]>): HttpResponse<Saison[]> {
        const jsonResponse: Saison[] = res.body;
        const body: Saison[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Saison.
     */
    private convertItemFromServer(saison: Saison): Saison {
        const copy: Saison = Object.assign({}, saison);
        copy.dateCreation = this.dateUtils
            .convertLocalDateFromServer(saison.dateCreation);
        return copy;
    }

    /**
     * Convert a Saison to a JSON which can be sent to the server.
     */
    private convert(saison: Saison): Saison {
        const copy: Saison = Object.assign({}, saison);
        copy.dateCreation = this.dateUtils
            .convertLocalDateToServer(saison.dateCreation);
        return copy;
    }
}
