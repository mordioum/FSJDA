import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { DojoClub } from './dojo-club.model';
import { DojoClubPopupService } from './dojo-club-popup.service';
import { DojoClubService } from './dojo-club.service';
import { User, UserService } from '../../shared';
import { Ligue, LigueService } from '../ligue';

@Component({
    selector: 'jhi-dojo-club-dialog',
    templateUrl: './dojo-club-dialog.component.html'
})
export class DojoClubDialogComponent implements OnInit {

    dojoClub: DojoClub;
    isSaving: boolean;

    users: User[];

    ligues: Ligue[];
    dateCreationDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private dojoClubService: DojoClubService,
        private userService: UserService,
        private ligueService: LigueService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.userService.query()
            .subscribe((res: HttpResponse<User[]>) => { this.users = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.ligueService.query()
            .subscribe((res: HttpResponse<Ligue[]>) => { this.ligues = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.dojoClub.id !== undefined) {
            this.subscribeToSaveResponse(
                this.dojoClubService.update(this.dojoClub));
        } else {
            this.subscribeToSaveResponse(
                this.dojoClubService.create(this.dojoClub));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<DojoClub>>) {
        result.subscribe((res: HttpResponse<DojoClub>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: DojoClub) {
        this.eventManager.broadcast({ name: 'dojoClubListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackUserById(index: number, item: User) {
        return item.id;
    }

    trackLigueById(index: number, item: Ligue) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-dojo-club-popup',
    template: ''
})
export class DojoClubPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private dojoClubPopupService: DojoClubPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.dojoClubPopupService
                    .open(DojoClubDialogComponent as Component, params['id']);
            } else {
                this.dojoClubPopupService
                    .open(DojoClubDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
