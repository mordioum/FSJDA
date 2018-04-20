import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Ligue } from './ligue.model';
import { LiguePopupService } from './ligue-popup.service';
import { LigueService } from './ligue.service';

@Component({
    selector: 'jhi-ligue-delete-dialog',
    templateUrl: './ligue-delete-dialog.component.html'
})
export class LigueDeleteDialogComponent {

    ligue: Ligue;

    constructor(
        private ligueService: LigueService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.ligueService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'ligueListModification',
                content: 'Deleted an ligue'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-ligue-delete-popup',
    template: ''
})
export class LigueDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private liguePopupService: LiguePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.liguePopupService
                .open(LigueDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
