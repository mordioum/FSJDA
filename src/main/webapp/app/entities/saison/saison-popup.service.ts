import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { Saison } from './saison.model';
import { SaisonService } from './saison.service';

@Injectable()
export class SaisonPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private saisonService: SaisonService

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
                this.saisonService.find(id)
                    .subscribe((saisonResponse: HttpResponse<Saison>) => {
                        const saison: Saison = saisonResponse.body;
                        if (saison.dateCreation) {
                            saison.dateCreation = {
                                year: saison.dateCreation.getFullYear(),
                                month: saison.dateCreation.getMonth() + 1,
                                day: saison.dateCreation.getDate()
                            };
                        }
                        this.ngbModalRef = this.saisonModalRef(component, saison);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.saisonModalRef(component, new Saison());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    saisonModalRef(component: Component, saison: Saison): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.saison = saison;
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
