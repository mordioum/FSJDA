import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { LigueComponent } from './ligue.component';
import { LigueDetailComponent } from './ligue-detail.component';
import { LiguePopupComponent } from './ligue-dialog.component';
import { LigueDeletePopupComponent } from './ligue-delete-dialog.component';

export const ligueRoute: Routes = [
    {
        path: 'ligue',
        component: LigueComponent,
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_DOJOCLUB', 'ROLE_LIGUE'],
            pageTitle: 'fsjdaApp.ligue.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'ligue/:id',
        component: LigueDetailComponent,
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_DOJOCLUB', 'ROLE_LIGUE'],
            pageTitle: 'fsjdaApp.ligue.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const liguePopupRoute: Routes = [
    {
        path: 'ligue-new',
        component: LiguePopupComponent,
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'fsjdaApp.ligue.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'ligue/:id/edit',
        component: LiguePopupComponent,
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'fsjdaApp.ligue.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'ligue/:id/delete',
        component: LigueDeletePopupComponent,
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'fsjdaApp.ligue.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
