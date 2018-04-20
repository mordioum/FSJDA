/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { FsjdaTestModule } from '../../../test.module';
import { DojoClubDialogComponent } from '../../../../../../main/webapp/app/entities/dojo-club/dojo-club-dialog.component';
import { DojoClubService } from '../../../../../../main/webapp/app/entities/dojo-club/dojo-club.service';
import { DojoClub } from '../../../../../../main/webapp/app/entities/dojo-club/dojo-club.model';
import { LigueService } from '../../../../../../main/webapp/app/entities/ligue';

describe('Component Tests', () => {

    describe('DojoClub Management Dialog Component', () => {
        let comp: DojoClubDialogComponent;
        let fixture: ComponentFixture<DojoClubDialogComponent>;
        let service: DojoClubService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FsjdaTestModule],
                declarations: [DojoClubDialogComponent],
                providers: [
                    LigueService,
                    DojoClubService
                ]
            })
            .overrideTemplate(DojoClubDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(DojoClubDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DojoClubService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new DojoClub(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.dojoClub = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'dojoClubListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new DojoClub();
                        spyOn(service, 'create').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.dojoClub = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'dojoClubListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
