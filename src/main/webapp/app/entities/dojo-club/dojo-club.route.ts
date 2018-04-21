import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { DojoClubComponent } from './dojo-club.component';
import { DojoClubDetailComponent } from './dojo-club-detail.component';
import { DojoClubPopupComponent } from './dojo-club-dialog.component';
import { DojoClubDeletePopupComponent } from './dojo-club-delete-dialog.component';

export const dojoClubRoute: Routes = [
    {
        path: 'dojo-club',
        component: DojoClubComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'fsjdaApp.dojoClub.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'dojo-club/:id',
        component: DojoClubDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'fsjdaApp.dojoClub.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const dojoClubPopupRoute: Routes = [
    {
        path: 'dojo-club-new',
        component: DojoClubPopupComponent,
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'fsjdaApp.dojoClub.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'dojo-club/:id/edit',
        component: DojoClubPopupComponent,
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'fsjdaApp.dojoClub.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'dojo-club/:id/delete',
        component: DojoClubDeletePopupComponent,
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'fsjdaApp.dojoClub.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
