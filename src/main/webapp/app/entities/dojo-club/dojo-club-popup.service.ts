import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { DojoClub } from './dojo-club.model';
import { DojoClubService } from './dojo-club.service';

@Injectable()
export class DojoClubPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private dojoClubService: DojoClubService

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
                this.dojoClubService.find(id)
                    .subscribe((dojoClubResponse: HttpResponse<DojoClub>) => {
                        const dojoClub: DojoClub = dojoClubResponse.body;
                        if (dojoClub.dateCreation) {
                            dojoClub.dateCreation = {
                                year: dojoClub.dateCreation.getFullYear(),
                                month: dojoClub.dateCreation.getMonth() + 1,
                                day: dojoClub.dateCreation.getDate()
                            };
                        }
                        this.ngbModalRef = this.dojoClubModalRef(component, dojoClub);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.dojoClubModalRef(component, new DojoClub());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    dojoClubModalRef(component: Component, dojoClub: DojoClub): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.dojoClub = dojoClub;
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
