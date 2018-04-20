import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { TarifCeinture } from './tarif-ceinture.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<TarifCeinture>;

@Injectable()
export class TarifCeintureService {

    private resourceUrl =  SERVER_API_URL + 'api/tarif-ceintures';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) { }

    create(tarifCeinture: TarifCeinture): Observable<EntityResponseType> {
        const copy = this.convert(tarifCeinture);
        return this.http.post<TarifCeinture>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(tarifCeinture: TarifCeinture): Observable<EntityResponseType> {
        const copy = this.convert(tarifCeinture);
        return this.http.put<TarifCeinture>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<TarifCeinture>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<TarifCeinture[]>> {
        const options = createRequestOption(req);
        return this.http.get<TarifCeinture[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<TarifCeinture[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: TarifCeinture = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<TarifCeinture[]>): HttpResponse<TarifCeinture[]> {
        const jsonResponse: TarifCeinture[] = res.body;
        const body: TarifCeinture[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to TarifCeinture.
     */
    private convertItemFromServer(tarifCeinture: TarifCeinture): TarifCeinture {
        const copy: TarifCeinture = Object.assign({}, tarifCeinture);
        copy.dateCreation = this.dateUtils
            .convertLocalDateFromServer(tarifCeinture.dateCreation);
        return copy;
    }

    /**
     * Convert a TarifCeinture to a JSON which can be sent to the server.
     */
    private convert(tarifCeinture: TarifCeinture): TarifCeinture {
        const copy: TarifCeinture = Object.assign({}, tarifCeinture);
        copy.dateCreation = this.dateUtils
            .convertLocalDateToServer(tarifCeinture.dateCreation);
        return copy;
    }
}
