import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { Athlete } from './athlete.model';
import { AthleteService } from './athlete.service';

@Component({
    selector: 'jhi-athlete-detail',
    templateUrl: './athlete-detail.component.html'
})
export class AthleteDetailComponent implements OnInit, OnDestroy {

    athlete: Athlete;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private athleteService: AthleteService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInAthletes();
    }

    load(id) {
        this.athleteService.find(id)
            .subscribe((athleteResponse: HttpResponse<Athlete>) => {
                this.athlete = athleteResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInAthletes() {
        this.eventSubscriber = this.eventManager.subscribe(
            'athleteListModification',
            (response) => this.load(this.athlete.id)
        );
    }
}
