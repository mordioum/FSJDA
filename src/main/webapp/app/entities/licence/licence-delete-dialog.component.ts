import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Licence } from './licence.model';
import { LicencePopupService } from './licence-popup.service';
import { LicenceService } from './licence.service';

@Component({
    selector: 'jhi-licence-delete-dialog',
    templateUrl: './licence-delete-dialog.component.html'
})
export class LicenceDeleteDialogComponent {

    licence: Licence;

    constructor(
        private licenceService: LicenceService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.licenceService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'licenceListModification',
                content: 'Deleted an licence'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-licence-delete-popup',
    template: ''
})
export class LicenceDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private licencePopupService: LicencePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.licencePopupService
                .open(LicenceDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
