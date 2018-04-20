import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { TarifCeinture } from './tarif-ceinture.model';
import { TarifCeintureService } from './tarif-ceinture.service';

@Component({
    selector: 'jhi-tarif-ceinture-detail',
    templateUrl: './tarif-ceinture-detail.component.html'
})
export class TarifCeintureDetailComponent implements OnInit, OnDestroy {

    tarifCeinture: TarifCeinture;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private tarifCeintureService: TarifCeintureService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInTarifCeintures();
    }

    load(id) {
        this.tarifCeintureService.find(id)
            .subscribe((tarifCeintureResponse: HttpResponse<TarifCeinture>) => {
                this.tarifCeinture = tarifCeintureResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInTarifCeintures() {
        this.eventSubscriber = this.eventManager.subscribe(
            'tarifCeintureListModification',
            (response) => this.load(this.tarifCeinture.id)
        );
    }
}
