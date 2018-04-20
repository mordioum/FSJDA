/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { FsjdaTestModule } from '../../../test.module';
import { LicenceDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/licence/licence-delete-dialog.component';
import { LicenceService } from '../../../../../../main/webapp/app/entities/licence/licence.service';

describe('Component Tests', () => {

    describe('Licence Management Delete Component', () => {
        let comp: LicenceDeleteDialogComponent;
        let fixture: ComponentFixture<LicenceDeleteDialogComponent>;
        let service: LicenceService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FsjdaTestModule],
                declarations: [LicenceDeleteDialogComponent],
                providers: [
                    LicenceService
                ]
            })
            .overrideTemplate(LicenceDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(LicenceDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(LicenceService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        spyOn(service, 'delete').and.returnValue(Observable.of({}));

                        // WHEN
                        comp.confirmDelete(123);
                        tick();

                        // THEN
                        expect(service.delete).toHaveBeenCalledWith(123);
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
