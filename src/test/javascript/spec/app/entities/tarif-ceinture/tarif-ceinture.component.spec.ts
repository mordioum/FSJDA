/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { FsjdaTestModule } from '../../../test.module';
import { TarifCeintureComponent } from '../../../../../../main/webapp/app/entities/tarif-ceinture/tarif-ceinture.component';
import { TarifCeintureService } from '../../../../../../main/webapp/app/entities/tarif-ceinture/tarif-ceinture.service';
import { TarifCeinture } from '../../../../../../main/webapp/app/entities/tarif-ceinture/tarif-ceinture.model';

describe('Component Tests', () => {

    describe('TarifCeinture Management Component', () => {
        let comp: TarifCeintureComponent;
        let fixture: ComponentFixture<TarifCeintureComponent>;
        let service: TarifCeintureService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FsjdaTestModule],
                declarations: [TarifCeintureComponent],
                providers: [
                    TarifCeintureService
                ]
            })
            .overrideTemplate(TarifCeintureComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(TarifCeintureComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TarifCeintureService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new TarifCeinture(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.tarifCeintures[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
