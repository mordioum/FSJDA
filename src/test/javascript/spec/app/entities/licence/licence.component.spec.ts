/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { FsjdaTestModule } from '../../../test.module';
import { LicenceComponent } from '../../../../../../main/webapp/app/entities/licence/licence.component';
import { LicenceService } from '../../../../../../main/webapp/app/entities/licence/licence.service';
import { Licence } from '../../../../../../main/webapp/app/entities/licence/licence.model';

describe('Component Tests', () => {

    describe('Licence Management Component', () => {
        let comp: LicenceComponent;
        let fixture: ComponentFixture<LicenceComponent>;
        let service: LicenceService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FsjdaTestModule],
                declarations: [LicenceComponent],
                providers: [
                    LicenceService
                ]
            })
            .overrideTemplate(LicenceComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(LicenceComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(LicenceService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new Licence(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.licences[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
