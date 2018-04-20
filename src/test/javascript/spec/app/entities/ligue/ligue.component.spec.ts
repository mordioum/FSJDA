/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { FsjdaTestModule } from '../../../test.module';
import { LigueComponent } from '../../../../../../main/webapp/app/entities/ligue/ligue.component';
import { LigueService } from '../../../../../../main/webapp/app/entities/ligue/ligue.service';
import { Ligue } from '../../../../../../main/webapp/app/entities/ligue/ligue.model';

describe('Component Tests', () => {

    describe('Ligue Management Component', () => {
        let comp: LigueComponent;
        let fixture: ComponentFixture<LigueComponent>;
        let service: LigueService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FsjdaTestModule],
                declarations: [LigueComponent],
                providers: [
                    LigueService
                ]
            })
            .overrideTemplate(LigueComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(LigueComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(LigueService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new Ligue(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.ligues[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
