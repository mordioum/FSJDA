/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { FsjdaTestModule } from '../../../test.module';
import { DojoClubComponent } from '../../../../../../main/webapp/app/entities/dojo-club/dojo-club.component';
import { DojoClubService } from '../../../../../../main/webapp/app/entities/dojo-club/dojo-club.service';
import { DojoClub } from '../../../../../../main/webapp/app/entities/dojo-club/dojo-club.model';

describe('Component Tests', () => {

    describe('DojoClub Management Component', () => {
        let comp: DojoClubComponent;
        let fixture: ComponentFixture<DojoClubComponent>;
        let service: DojoClubService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FsjdaTestModule],
                declarations: [DojoClubComponent],
                providers: [
                    DojoClubService
                ]
            })
            .overrideTemplate(DojoClubComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(DojoClubComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DojoClubService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new DojoClub(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.dojoClubs[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
