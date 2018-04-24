import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { SaisonComponent } from './saison.component';
import { SaisonDetailComponent } from './saison-detail.component';
import { SaisonPopupComponent } from './saison-dialog.component';
import { SaisonDeletePopupComponent } from './saison-delete-dialog.component';

export const saisonRoute: Routes = [
    {
        path: 'saison',
        component: SaisonComponent,
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_DOJOCLUB', 'ROLE_LIGUE'],
            pageTitle: 'fsjdaApp.saison.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'saison/:id',
        component: SaisonDetailComponent,
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_DOJOCLUB', 'ROLE_LIGUE'],
            pageTitle: 'fsjdaApp.saison.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const saisonPopupRoute: Routes = [
    {
        path: 'saison-new',
        component: SaisonPopupComponent,
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'fsjdaApp.saison.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'saison/:id/edit',
        component: SaisonPopupComponent,
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'fsjdaApp.saison.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'saison/:id/delete',
        component: SaisonDeletePopupComponent,
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'fsjdaApp.saison.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
