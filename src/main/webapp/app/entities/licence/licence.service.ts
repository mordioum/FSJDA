import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { Licence } from './licence.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Licence>;

@Injectable()
export class LicenceService {

    private resourceUrl =  SERVER_API_URL + 'api/licences';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) { }

    create(licence: Licence): Observable<EntityResponseType> {
        const copy = this.convert(licence);
        return this.http.post<Licence>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(licence: Licence): Observable<EntityResponseType> {
        const copy = this.convert(licence);
        return this.http.put<Licence>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Licence>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Licence[]>> {
        const options = createRequestOption(req);
        return this.http.get<Licence[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Licence[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Licence = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Licence[]>): HttpResponse<Licence[]> {
        const jsonResponse: Licence[] = res.body;
        const body: Licence[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Licence.
     */
    private convertItemFromServer(licence: Licence): Licence {
        const copy: Licence = Object.assign({}, licence);
        copy.dateCreation = this.dateUtils
            .convertLocalDateFromServer(licence.dateCreation);
        return copy;
    }

    /**
     * Convert a Licence to a JSON which can be sent to the server.
     */
    private convert(licence: Licence): Licence {
        const copy: Licence = Object.assign({}, licence);
        copy.dateCreation = this.dateUtils
            .convertLocalDateToServer(licence.dateCreation);
        return copy;
    }
}
