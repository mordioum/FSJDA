/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { FsjdaTestModule } from '../../../test.module';
import { SaisonComponent } from '../../../../../../main/webapp/app/entities/saison/saison.component';
import { SaisonService } from '../../../../../../main/webapp/app/entities/saison/saison.service';
import { Saison } from '../../../../../../main/webapp/app/entities/saison/saison.model';

describe('Component Tests', () => {

    describe('Saison Management Component', () => {
        let comp: SaisonComponent;
        let fixture: ComponentFixture<SaisonComponent>;
        let service: SaisonService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FsjdaTestModule],
                declarations: [SaisonComponent],
                providers: [
                    SaisonService
                ]
            })
            .overrideTemplate(SaisonComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(SaisonComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SaisonService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new Saison(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.saisons[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
