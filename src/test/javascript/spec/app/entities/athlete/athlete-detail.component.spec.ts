/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { FsjdaTestModule } from '../../../test.module';
import { AthleteDetailComponent } from '../../../../../../main/webapp/app/entities/athlete/athlete-detail.component';
import { AthleteService } from '../../../../../../main/webapp/app/entities/athlete/athlete.service';
import { Athlete } from '../../../../../../main/webapp/app/entities/athlete/athlete.model';

describe('Component Tests', () => {

    describe('Athlete Management Detail Component', () => {
        let comp: AthleteDetailComponent;
        let fixture: ComponentFixture<AthleteDetailComponent>;
        let service: AthleteService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FsjdaTestModule],
                declarations: [AthleteDetailComponent],
                providers: [
                    AthleteService
                ]
            })
            .overrideTemplate(AthleteDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(AthleteDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AthleteService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new Athlete(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.athlete).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
