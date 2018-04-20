import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { DojoClub } from './dojo-club.model';
import { DojoClubService } from './dojo-club.service';

@Component({
    selector: 'jhi-dojo-club-detail',
    templateUrl: './dojo-club-detail.component.html'
})
export class DojoClubDetailComponent implements OnInit, OnDestroy {

    dojoClub: DojoClub;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private dojoClubService: DojoClubService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInDojoClubs();
    }

    load(id) {
        this.dojoClubService.find(id)
            .subscribe((dojoClubResponse: HttpResponse<DojoClub>) => {
                this.dojoClub = dojoClubResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInDojoClubs() {
        this.eventSubscriber = this.eventManager.subscribe(
            'dojoClubListModification',
            (response) => this.load(this.dojoClub.id)
        );
    }
}
