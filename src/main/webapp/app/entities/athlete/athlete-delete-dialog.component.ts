import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Athlete } from './athlete.model';
import { AthletePopupService } from './athlete-popup.service';
import { AthleteService } from './athlete.service';

@Component({
    selector: 'jhi-athlete-delete-dialog',
    templateUrl: './athlete-delete-dialog.component.html'
})
export class AthleteDeleteDialogComponent {

    athlete: Athlete;

    constructor(
        private athleteService: AthleteService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.athleteService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'athleteListModification',
                content: 'Deleted an athlete'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-athlete-delete-popup',
    template: ''
})
export class AthleteDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private athletePopupService: AthletePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.athletePopupService
                .open(AthleteDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
