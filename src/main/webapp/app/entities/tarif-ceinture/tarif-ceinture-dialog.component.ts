import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { TarifCeinture } from './tarif-ceinture.model';
import { TarifCeinturePopupService } from './tarif-ceinture-popup.service';
import { TarifCeintureService } from './tarif-ceinture.service';

@Component({
    selector: 'jhi-tarif-ceinture-dialog',
    templateUrl: './tarif-ceinture-dialog.component.html'
})
export class TarifCeintureDialogComponent implements OnInit {

    tarifCeinture: TarifCeinture;
    isSaving: boolean;
    dateCreationDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private tarifCeintureService: TarifCeintureService,
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
        if (this.tarifCeinture.id !== undefined) {
            this.subscribeToSaveResponse(
                this.tarifCeintureService.update(this.tarifCeinture));
        } else {
            this.subscribeToSaveResponse(
                this.tarifCeintureService.create(this.tarifCeinture));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<TarifCeinture>>) {
        result.subscribe((res: HttpResponse<TarifCeinture>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: TarifCeinture) {
        this.eventManager.broadcast({ name: 'tarifCeintureListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-tarif-ceinture-popup',
    template: ''
})
export class TarifCeinturePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private tarifCeinturePopupService: TarifCeinturePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.tarifCeinturePopupService
                    .open(TarifCeintureDialogComponent as Component, params['id']);
            } else {
                this.tarifCeinturePopupService
                    .open(TarifCeintureDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
