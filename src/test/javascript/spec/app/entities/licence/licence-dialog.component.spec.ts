/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { FsjdaTestModule } from '../../../test.module';
import { LicenceDialogComponent } from '../../../../../../main/webapp/app/entities/licence/licence-dialog.component';
import { LicenceService } from '../../../../../../main/webapp/app/entities/licence/licence.service';
import { Licence } from '../../../../../../main/webapp/app/entities/licence/licence.model';
import { AthleteService } from '../../../../../../main/webapp/app/entities/athlete';
import { TarifCeintureService } from '../../../../../../main/webapp/app/entities/tarif-ceinture';
import { SaisonService } from '../../../../../../main/webapp/app/entities/saison';

describe('Component Tests', () => {

    describe('Licence Management Dialog Component', () => {
        let comp: LicenceDialogComponent;
        let fixture: ComponentFixture<LicenceDialogComponent>;
        let service: LicenceService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FsjdaTestModule],
                declarations: [LicenceDialogComponent],
                providers: [
                    AthleteService,
                    TarifCeintureService,
                    SaisonService,
                    LicenceService
                ]
            })
            .overrideTemplate(LicenceDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(LicenceDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(LicenceService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new Licence(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.licence = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'licenceListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new Licence();
                        spyOn(service, 'create').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.licence = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'licenceListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
