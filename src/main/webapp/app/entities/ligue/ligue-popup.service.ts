import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { Ligue } from './ligue.model';
import { LigueService } from './ligue.service';

@Injectable()
export class LiguePopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private ligueService: LigueService

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
                this.ligueService.find(id)
                    .subscribe((ligueResponse: HttpResponse<Ligue>) => {
                        const ligue: Ligue = ligueResponse.body;
                        if (ligue.dateCreation) {
                            ligue.dateCreation = {
                                year: ligue.dateCreation.getFullYear(),
                                month: ligue.dateCreation.getMonth() + 1,
                                day: ligue.dateCreation.getDate()
                            };
                        }
                        this.ngbModalRef = this.ligueModalRef(component, ligue);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.ligueModalRef(component, new Ligue());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    ligueModalRef(component: Component, ligue: Ligue): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.ligue = ligue;
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
