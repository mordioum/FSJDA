import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Athlete } from './athlete.model';
import { AthletePopupService } from './athlete-popup.service';
import { AthleteService } from './athlete.service';
import { DojoClub, DojoClubService } from '../dojo-club';

@Component({
    selector: 'jhi-athlete-dialog',
    templateUrl: './athlete-dialog.component.html'
})
export class AthleteDialogComponent implements OnInit {

    athlete: Athlete;
    isSaving: boolean;

    dojoclubs: DojoClub[];
    dateCreationDp: any;
    dateNaissanceDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private athleteService: AthleteService,
        private dojoClubService: DojoClubService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.dojoClubService.query()
            .subscribe((res: HttpResponse<DojoClub[]>) => { this.dojoclubs = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.athlete.id !== undefined) {
            this.subscribeToSaveResponse(
                this.athleteService.update(this.athlete));
        } else {
            this.subscribeToSaveResponse(
                this.athleteService.create(this.athlete));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Athlete>>) {
        result.subscribe((res: HttpResponse<Athlete>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Athlete) {
        this.eventManager.broadcast({ name: 'athleteListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackDojoClubById(index: number, item: DojoClub) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-athlete-popup',
    template: ''
})
export class AthletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private athletePopupService: AthletePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.athletePopupService
                    .open(AthleteDialogComponent as Component, params['id']);
            } else {
                this.athletePopupService
                    .open(AthleteDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
