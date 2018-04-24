import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Ligue } from './ligue.model';
import { LiguePopupService } from './ligue-popup.service';
import { LigueService } from './ligue.service';
import { User, UserService } from '../../shared';
import { Discipline, DisciplineService } from '../discipline';

@Component({
    selector: 'jhi-ligue-dialog',
    templateUrl: './ligue-dialog.component.html'
})
export class LigueDialogComponent implements OnInit {

    ligue: Ligue;
    isSaving: boolean;

    users: User[];

    disciplines: Discipline[];
    dateCreationDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private ligueService: LigueService,
        private userService: UserService,
        private disciplineService: DisciplineService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.userService.query()
            .subscribe((res: HttpResponse<User[]>) => { this.users = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.disciplineService.query()
            .subscribe((res: HttpResponse<Discipline[]>) => { this.disciplines = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.ligue.id !== undefined) {
            this.subscribeToSaveResponse(
                this.ligueService.update(this.ligue));
        } else {
            this.subscribeToSaveResponse(
                this.ligueService.create(this.ligue));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Ligue>>) {
        result.subscribe((res: HttpResponse<Ligue>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Ligue) {
        this.eventManager.broadcast({ name: 'ligueListModification', content: 'OK'});
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

    trackDisciplineById(index: number, item: Discipline) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-ligue-popup',
    template: ''
})
export class LiguePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private liguePopupService: LiguePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.liguePopupService
                    .open(LigueDialogComponent as Component, params['id']);
            } else {
                this.liguePopupService
                    .open(LigueDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
