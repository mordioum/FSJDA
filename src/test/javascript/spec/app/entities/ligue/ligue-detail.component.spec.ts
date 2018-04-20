/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { FsjdaTestModule } from '../../../test.module';
import { LigueDetailComponent } from '../../../../../../main/webapp/app/entities/ligue/ligue-detail.component';
import { LigueService } from '../../../../../../main/webapp/app/entities/ligue/ligue.service';
import { Ligue } from '../../../../../../main/webapp/app/entities/ligue/ligue.model';

describe('Component Tests', () => {

    describe('Ligue Management Detail Component', () => {
        let comp: LigueDetailComponent;
        let fixture: ComponentFixture<LigueDetailComponent>;
        let service: LigueService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FsjdaTestModule],
                declarations: [LigueDetailComponent],
                providers: [
                    LigueService
                ]
            })
            .overrideTemplate(LigueDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(LigueDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(LigueService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new Ligue(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.ligue).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
