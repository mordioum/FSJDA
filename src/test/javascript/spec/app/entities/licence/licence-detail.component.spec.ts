/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { FsjdaTestModule } from '../../../test.module';
import { LicenceDetailComponent } from '../../../../../../main/webapp/app/entities/licence/licence-detail.component';
import { LicenceService } from '../../../../../../main/webapp/app/entities/licence/licence.service';
import { Licence } from '../../../../../../main/webapp/app/entities/licence/licence.model';

describe('Component Tests', () => {

    describe('Licence Management Detail Component', () => {
        let comp: LicenceDetailComponent;
        let fixture: ComponentFixture<LicenceDetailComponent>;
        let service: LicenceService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FsjdaTestModule],
                declarations: [LicenceDetailComponent],
                providers: [
                    LicenceService
                ]
            })
            .overrideTemplate(LicenceDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(LicenceDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(LicenceService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new Licence(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.licence).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
