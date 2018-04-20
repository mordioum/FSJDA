import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Saison } from './saison.model';
import { SaisonPopupService } from './saison-popup.service';
import { SaisonService } from './saison.service';

@Component({
    selector: 'jhi-saison-delete-dialog',
    templateUrl: './saison-delete-dialog.component.html'
})
export class SaisonDeleteDialogComponent {

    saison: Saison;

    constructor(
        private saisonService: SaisonService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.saisonService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'saisonListModification',
                content: 'Deleted an saison'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-saison-delete-popup',
    template: ''
})
export class SaisonDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private saisonPopupService: SaisonPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.saisonPopupService
                .open(SaisonDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
