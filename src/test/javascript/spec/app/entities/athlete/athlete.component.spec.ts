/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { FsjdaTestModule } from '../../../test.module';
import { AthleteComponent } from '../../../../../../main/webapp/app/entities/athlete/athlete.component';
import { AthleteService } from '../../../../../../main/webapp/app/entities/athlete/athlete.service';
import { Athlete } from '../../../../../../main/webapp/app/entities/athlete/athlete.model';

describe('Component Tests', () => {

    describe('Athlete Management Component', () => {
        let comp: AthleteComponent;
        let fixture: ComponentFixture<AthleteComponent>;
        let service: AthleteService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FsjdaTestModule],
                declarations: [AthleteComponent],
                providers: [
                    AthleteService
                ]
            })
            .overrideTemplate(AthleteComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(AthleteComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AthleteService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new Athlete(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.athletes[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
