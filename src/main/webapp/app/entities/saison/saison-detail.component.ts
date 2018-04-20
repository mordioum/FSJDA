import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { Saison } from './saison.model';
import { SaisonService } from './saison.service';

@Component({
    selector: 'jhi-saison-detail',
    templateUrl: './saison-detail.component.html'
})
export class SaisonDetailComponent implements OnInit, OnDestroy {

    saison: Saison;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private saisonService: SaisonService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInSaisons();
    }

    load(id) {
        this.saisonService.find(id)
            .subscribe((saisonResponse: HttpResponse<Saison>) => {
                this.saison = saisonResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInSaisons() {
        this.eventSubscriber = this.eventManager.subscribe(
            'saisonListModification',
            (response) => this.load(this.saison.id)
        );
    }
}
