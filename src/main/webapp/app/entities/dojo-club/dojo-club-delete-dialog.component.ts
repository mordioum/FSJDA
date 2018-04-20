import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { DojoClub } from './dojo-club.model';
import { DojoClubPopupService } from './dojo-club-popup.service';
import { DojoClubService } from './dojo-club.service';

@Component({
    selector: 'jhi-dojo-club-delete-dialog',
    templateUrl: './dojo-club-delete-dialog.component.html'
})
export class DojoClubDeleteDialogComponent {

    dojoClub: DojoClub;

    constructor(
        private dojoClubService: DojoClubService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.dojoClubService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'dojoClubListModification',
                content: 'Deleted an dojoClub'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-dojo-club-delete-popup',
    template: ''
})
export class DojoClubDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private dojoClubPopupService: DojoClubPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.dojoClubPopupService
                .open(DojoClubDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
