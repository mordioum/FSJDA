import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { AthleteComponent } from './athlete.component';
import { AthleteDetailComponent } from './athlete-detail.component';
import { AthletePopupComponent } from './athlete-dialog.component';
import { AthleteDeletePopupComponent } from './athlete-delete-dialog.component';

export const athleteRoute: Routes = [
    {
        path: 'athlete',
        component: AthleteComponent,
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_DOJOCLUB', 'ROLE_LIGUE'],
            pageTitle: 'fsjdaApp.athlete.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'athlete/:id',
        component: AthleteDetailComponent,
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_DOJOCLUB', 'ROLE_LIGUE'],
            pageTitle: 'fsjdaApp.athlete.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const athletePopupRoute: Routes = [
    {
        path: 'athlete-new',
        component: AthletePopupComponent,
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_DOJOCLUB'],
            pageTitle: 'fsjdaApp.athlete.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'athlete/:id/edit',
        component: AthletePopupComponent,
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_DOJOCLUB'],
            pageTitle: 'fsjdaApp.athlete.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'athlete/:id/delete',
        component: AthleteDeletePopupComponent,
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_DOJOCLUB'],
            pageTitle: 'fsjdaApp.athlete.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
