import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { Athlete } from './athlete.model';
import { AthleteService } from './athlete.service';

@Injectable()
export class AthletePopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private athleteService: AthleteService

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
                this.athleteService.find(id)
                    .subscribe((athleteResponse: HttpResponse<Athlete>) => {
                        const athlete: Athlete = athleteResponse.body;
                        if (athlete.dateCreation) {
                            athlete.dateCreation = {
                                year: athlete.dateCreation.getFullYear(),
                                month: athlete.dateCreation.getMonth() + 1,
                                day: athlete.dateCreation.getDate()
                            };
                        }
                        if (athlete.dateNaissance) {
                            athlete.dateNaissance = {
                                year: athlete.dateNaissance.getFullYear(),
                                month: athlete.dateNaissance.getMonth() + 1,
                                day: athlete.dateNaissance.getDate()
                            };
                        }
                        this.ngbModalRef = this.athleteModalRef(component, athlete);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.athleteModalRef(component, new Athlete());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    athleteModalRef(component: Component, athlete: Athlete): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.athlete = athlete;
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
