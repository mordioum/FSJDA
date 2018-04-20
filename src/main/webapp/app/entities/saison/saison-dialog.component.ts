import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Saison } from './saison.model';
import { SaisonPopupService } from './saison-popup.service';
import { SaisonService } from './saison.service';

@Component({
    selector: 'jhi-saison-dialog',
    templateUrl: './saison-dialog.component.html'
})
export class SaisonDialogComponent implements OnInit {

    saison: Saison;
    isSaving: boolean;
    dateCreationDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private saisonService: SaisonService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.saison.id !== undefined) {
            this.subscribeToSaveResponse(
                this.saisonService.update(this.saison));
        } else {
            this.subscribeToSaveResponse(
                this.saisonService.create(this.saison));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Saison>>) {
        result.subscribe((res: HttpResponse<Saison>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Saison) {
        this.eventManager.broadcast({ name: 'saisonListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-saison-popup',
    template: ''
})
export class SaisonPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private saisonPopupService: SaisonPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.saisonPopupService
                    .open(SaisonDialogComponent as Component, params['id']);
            } else {
                this.saisonPopupService
                    .open(SaisonDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
