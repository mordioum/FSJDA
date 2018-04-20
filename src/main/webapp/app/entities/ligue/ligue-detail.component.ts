import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { Ligue } from './ligue.model';
import { LigueService } from './ligue.service';

@Component({
    selector: 'jhi-ligue-detail',
    templateUrl: './ligue-detail.component.html'
})
export class LigueDetailComponent implements OnInit, OnDestroy {

    ligue: Ligue;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private ligueService: LigueService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInLigues();
    }

    load(id) {
        this.ligueService.find(id)
            .subscribe((ligueResponse: HttpResponse<Ligue>) => {
                this.ligue = ligueResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInLigues() {
        this.eventSubscriber = this.eventManager.subscribe(
            'ligueListModification',
            (response) => this.load(this.ligue.id)
        );
    }
}
