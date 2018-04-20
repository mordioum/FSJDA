/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { FsjdaTestModule } from '../../../test.module';
import { TarifCeintureDialogComponent } from '../../../../../../main/webapp/app/entities/tarif-ceinture/tarif-ceinture-dialog.component';
import { TarifCeintureService } from '../../../../../../main/webapp/app/entities/tarif-ceinture/tarif-ceinture.service';
import { TarifCeinture } from '../../../../../../main/webapp/app/entities/tarif-ceinture/tarif-ceinture.model';

describe('Component Tests', () => {

    describe('TarifCeinture Management Dialog Component', () => {
        let comp: TarifCeintureDialogComponent;
        let fixture: ComponentFixture<TarifCeintureDialogComponent>;
        let service: TarifCeintureService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FsjdaTestModule],
                declarations: [TarifCeintureDialogComponent],
                providers: [
                    TarifCeintureService
                ]
            })
            .overrideTemplate(TarifCeintureDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(TarifCeintureDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TarifCeintureService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new TarifCeinture(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.tarifCeinture = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'tarifCeintureListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new TarifCeinture();
                        spyOn(service, 'create').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.tarifCeinture = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'tarifCeintureListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
