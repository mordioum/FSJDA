/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { FsjdaTestModule } from '../../../test.module';
import { SaisonDetailComponent } from '../../../../../../main/webapp/app/entities/saison/saison-detail.component';
import { SaisonService } from '../../../../../../main/webapp/app/entities/saison/saison.service';
import { Saison } from '../../../../../../main/webapp/app/entities/saison/saison.model';

describe('Component Tests', () => {

    describe('Saison Management Detail Component', () => {
        let comp: SaisonDetailComponent;
        let fixture: ComponentFixture<SaisonDetailComponent>;
        let service: SaisonService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FsjdaTestModule],
                declarations: [SaisonDetailComponent],
                providers: [
                    SaisonService
                ]
            })
            .overrideTemplate(SaisonDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(SaisonDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SaisonService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new Saison(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.saison).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
