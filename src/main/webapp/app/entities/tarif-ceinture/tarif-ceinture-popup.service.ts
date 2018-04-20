import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { TarifCeinture } from './tarif-ceinture.model';
import { TarifCeintureService } from './tarif-ceinture.service';

@Injectable()
export class TarifCeinturePopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private tarifCeintureService: TarifCeintureService

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
                this.tarifCeintureService.find(id)
                    .subscribe((tarifCeintureResponse: HttpResponse<TarifCeinture>) => {
                        const tarifCeinture: TarifCeinture = tarifCeintureResponse.body;
                        if (tarifCeinture.dateCreation) {
                            tarifCeinture.dateCreation = {
                                year: tarifCeinture.dateCreation.getFullYear(),
                                month: tarifCeinture.dateCreation.getMonth() + 1,
                                day: tarifCeinture.dateCreation.getDate()
                            };
                        }
                        this.ngbModalRef = this.tarifCeintureModalRef(component, tarifCeinture);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.tarifCeintureModalRef(component, new TarifCeinture());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    tarifCeintureModalRef(component: Component, tarifCeinture: TarifCeinture): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.tarifCeinture = tarifCeinture;
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
