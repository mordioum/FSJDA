import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';

import { Licence } from './licence.model';
import { LicenceService } from './licence.service';

@Component({
    selector: 'jhi-licence-detail',
    templateUrl: './licence-detail.component.html'
})
export class LicenceDetailComponent implements OnInit, OnDestroy {

    licence: Licence;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private dataUtils: JhiDataUtils,
        private licenceService: LicenceService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInLicences();
    }

    load(id) {
        this.licenceService.find(id)
            .subscribe((licenceResponse: HttpResponse<Licence>) => {
                this.licence = licenceResponse.body;
            });
    }
    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInLicences() {
        this.eventSubscriber = this.eventManager.subscribe(
            'licenceListModification',
            (response) => this.load(this.licence.id)
        );
    }
}
