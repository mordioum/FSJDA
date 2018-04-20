import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { Athlete } from './athlete.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Athlete>;

@Injectable()
export class AthleteService {

    private resourceUrl =  SERVER_API_URL + 'api/athletes';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) { }

    create(athlete: Athlete): Observable<EntityResponseType> {
        const copy = this.convert(athlete);
        return this.http.post<Athlete>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(athlete: Athlete): Observable<EntityResponseType> {
        const copy = this.convert(athlete);
        return this.http.put<Athlete>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Athlete>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Athlete[]>> {
        const options = createRequestOption(req);
        return this.http.get<Athlete[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Athlete[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Athlete = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Athlete[]>): HttpResponse<Athlete[]> {
        const jsonResponse: Athlete[] = res.body;
        const body: Athlete[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Athlete.
     */
    private convertItemFromServer(athlete: Athlete): Athlete {
        const copy: Athlete = Object.assign({}, athlete);
        copy.dateCreation = this.dateUtils
            .convertLocalDateFromServer(athlete.dateCreation);
        copy.dateNaissance = this.dateUtils
            .convertLocalDateFromServer(athlete.dateNaissance);
        return copy;
    }

    /**
     * Convert a Athlete to a JSON which can be sent to the server.
     */
    private convert(athlete: Athlete): Athlete {
        const copy: Athlete = Object.assign({}, athlete);
        copy.dateCreation = this.dateUtils
            .convertLocalDateToServer(athlete.dateCreation);
        copy.dateNaissance = this.dateUtils
            .convertLocalDateToServer(athlete.dateNaissance);
        return copy;
    }
}
