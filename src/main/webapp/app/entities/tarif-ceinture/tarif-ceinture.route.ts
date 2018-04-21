import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { TarifCeintureComponent } from './tarif-ceinture.component';
import { TarifCeintureDetailComponent } from './tarif-ceinture-detail.component';
import { TarifCeinturePopupComponent } from './tarif-ceinture-dialog.component';
import { TarifCeintureDeletePopupComponent } from './tarif-ceinture-delete-dialog.component';

export const tarifCeintureRoute: Routes = [
    {
        path: 'tarif-ceinture',
        component: TarifCeintureComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'fsjdaApp.tarifCeinture.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'tarif-ceinture/:id',
        component: TarifCeintureDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'fsjdaApp.tarifCeinture.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const tarifCeinturePopupRoute: Routes = [
    {
        path: 'tarif-ceinture-new',
        component: TarifCeinturePopupComponent,
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'fsjdaApp.tarifCeinture.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'tarif-ceinture/:id/edit',
        component: TarifCeinturePopupComponent,
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'fsjdaApp.tarifCeinture.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'tarif-ceinture/:id/delete',
        component: TarifCeintureDeletePopupComponent,
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'fsjdaApp.tarifCeinture.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
