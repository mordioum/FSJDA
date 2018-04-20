import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { TarifCeinture } from './tarif-ceinture.model';
import { TarifCeinturePopupService } from './tarif-ceinture-popup.service';
import { TarifCeintureService } from './tarif-ceinture.service';

@Component({
    selector: 'jhi-tarif-ceinture-delete-dialog',
    templateUrl: './tarif-ceinture-delete-dialog.component.html'
})
export class TarifCeintureDeleteDialogComponent {

    tarifCeinture: TarifCeinture;

    constructor(
        private tarifCeintureService: TarifCeintureService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.tarifCeintureService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'tarifCeintureListModification',
                content: 'Deleted an tarifCeinture'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-tarif-ceinture-delete-popup',
    template: ''
})
export class TarifCeintureDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private tarifCeinturePopupService: TarifCeinturePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.tarifCeinturePopupService
                .open(TarifCeintureDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
