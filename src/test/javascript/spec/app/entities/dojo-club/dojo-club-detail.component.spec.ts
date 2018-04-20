/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { FsjdaTestModule } from '../../../test.module';
import { DojoClubDetailComponent } from '../../../../../../main/webapp/app/entities/dojo-club/dojo-club-detail.component';
import { DojoClubService } from '../../../../../../main/webapp/app/entities/dojo-club/dojo-club.service';
import { DojoClub } from '../../../../../../main/webapp/app/entities/dojo-club/dojo-club.model';

describe('Component Tests', () => {

    describe('DojoClub Management Detail Component', () => {
        let comp: DojoClubDetailComponent;
        let fixture: ComponentFixture<DojoClubDetailComponent>;
        let service: DojoClubService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FsjdaTestModule],
                declarations: [DojoClubDetailComponent],
                providers: [
                    DojoClubService
                ]
            })
            .overrideTemplate(DojoClubDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(DojoClubDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DojoClubService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new DojoClub(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.dojoClub).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
