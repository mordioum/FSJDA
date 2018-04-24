import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { LicenceComponent } from './licence.component';
import { LicenceDetailComponent } from './licence-detail.component';
import { LicencePopupComponent } from './licence-dialog.component';
import { LicenceDeletePopupComponent } from './licence-delete-dialog.component';

export const licenceRoute: Routes = [
    {
        path: 'licence',
        component: LicenceComponent,
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_DOJOCLUB', 'ROLE_LIGUE'],
            pageTitle: 'fsjdaApp.licence.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'licence/:id',
        component: LicenceDetailComponent,
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_DOJOCLUB', 'ROLE_LIGUE'],
            pageTitle: 'fsjdaApp.licence.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const licencePopupRoute: Routes = [
    {
        path: 'licence-new',
        component: LicencePopupComponent,
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_DOJOCLUB'],
            pageTitle: 'fsjdaApp.licence.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'licence/:id/edit',
        component: LicencePopupComponent,
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_DOJOCLUB'],
            pageTitle: 'fsjdaApp.licence.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'licence/:id/delete',
        component: LicenceDeletePopupComponent,
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_DOJOCLUB'],
            pageTitle: 'fsjdaApp.licence.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
