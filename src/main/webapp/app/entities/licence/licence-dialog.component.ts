import { Component, OnInit, OnDestroy, ElementRef } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { Licence } from './licence.model';
import { LicencePopupService } from './licence-popup.service';
import { LicenceService } from './licence.service';
import { Athlete, AthleteService } from '../athlete';
import { TarifCeinture, TarifCeintureService } from '../tarif-ceinture';
import { Saison, SaisonService } from '../saison';

@Component({
    selector: 'jhi-licence-dialog',
    templateUrl: './licence-dialog.component.html'
})
export class LicenceDialogComponent implements OnInit {

    licence: Licence;
    isSaving: boolean;

    athletes: Athlete[];

    tarifceintures: TarifCeinture[];

    saisons: Saison[];
    dateCreationDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private dataUtils: JhiDataUtils,
        private jhiAlertService: JhiAlertService,
        private licenceService: LicenceService,
        private athleteService: AthleteService,
        private tarifCeintureService: TarifCeintureService,
        private saisonService: SaisonService,
        private elementRef: ElementRef,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.athleteService.query()
            .subscribe((res: HttpResponse<Athlete[]>) => { this.athletes = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.tarifCeintureService.query()
            .subscribe((res: HttpResponse<TarifCeinture[]>) => { this.tarifceintures = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.saisonService.query()
            .subscribe((res: HttpResponse<Saison[]>) => { this.saisons = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }

    setFileData(event, entity, field, isImage) {
        this.dataUtils.setFileData(event, entity, field, isImage);
    }

    clearInputImage(field: string, fieldContentType: string, idInput: string) {
        this.dataUtils.clearInputImage(this.licence, this.elementRef, field, fieldContentType, idInput);
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.licence.id !== undefined) {
            this.subscribeToSaveResponse(
                this.licenceService.update(this.licence));
        } else {
            this.subscribeToSaveResponse(
                this.licenceService.create(this.licence));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Licence>>) {
        result.subscribe((res: HttpResponse<Licence>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Licence) {
        this.eventManager.broadcast({ name: 'licenceListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackAthleteById(index: number, item: Athlete) {
        return item.id;
    }

    trackTarifCeintureById(index: number, item: TarifCeinture) {
        return item.id;
    }

    trackSaisonById(index: number, item: Saison) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-licence-popup',
    template: ''
})
export class LicencePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private licencePopupService: LicencePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.licencePopupService
                    .open(LicenceDialogComponent as Component, params['id']);
            } else {
                this.licencePopupService
                    .open(LicenceDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
