import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { Licence } from './licence.model';
import { LicenceService } from './licence.service';

@Injectable()
export class LicencePopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private licenceService: LicenceService

    ) {
        this.ngbModalRef = null;
    }

    open(component: Component, id?: number | any): Promise<NgbModalRef> {
        return new Promise<NgbModalRef>((resolve, reject) => {
            const isOpen = this.ngbModalRef !== null;
            if (isOpen) {
                resolve(this.ngbModalRef);
            }

            if (id) {
                this.licenceService.find(id)
                    .subscribe((licenceResponse: HttpResponse<Licence>) => {
                        const licence: Licence = licenceResponse.body;
                        if (licence.dateCreation) {
                            licence.dateCreation = {
                                year: licence.dateCreation.getFullYear(),
                                month: licence.dateCreation.getMonth() + 1,
                                day: licence.dateCreation.getDate()
                            };
                        }
                        this.ngbModalRef = this.licenceModalRef(component, licence);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.licenceModalRef(component, new Licence());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    licenceModalRef(component: Component, licence: Licence): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.licence = licence;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
        });
        return modalRef;
    }
}
