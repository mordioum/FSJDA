/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { FsjdaTestModule } from '../../../test.module';
import { TarifCeintureDetailComponent } from '../../../../../../main/webapp/app/entities/tarif-ceinture/tarif-ceinture-detail.component';
import { TarifCeintureService } from '../../../../../../main/webapp/app/entities/tarif-ceinture/tarif-ceinture.service';
import { TarifCeinture } from '../../../../../../main/webapp/app/entities/tarif-ceinture/tarif-ceinture.model';

describe('Component Tests', () => {

    describe('TarifCeinture Management Detail Component', () => {
        let comp: TarifCeintureDetailComponent;
        let fixture: ComponentFixture<TarifCeintureDetailComponent>;
        let service: TarifCeintureService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FsjdaTestModule],
                declarations: [TarifCeintureDetailComponent],
                providers: [
                    TarifCeintureService
                ]
            })
            .overrideTemplate(TarifCeintureDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(TarifCeintureDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TarifCeintureService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new TarifCeinture(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.tarifCeinture).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
