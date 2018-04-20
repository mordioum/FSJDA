import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FsjdaSharedModule } from '../../shared';
import {
    DojoClubService,
    DojoClubPopupService,
    DojoClubComponent,
    DojoClubDetailComponent,
    DojoClubDialogComponent,
    DojoClubPopupComponent,
    DojoClubDeletePopupComponent,
    DojoClubDeleteDialogComponent,
    dojoClubRoute,
    dojoClubPopupRoute,
} from './';

const ENTITY_STATES = [
    ...dojoClubRoute,
    ...dojoClubPopupRoute,
];

@NgModule({
    imports: [
        FsjdaSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        DojoClubComponent,
        DojoClubDetailComponent,
        DojoClubDialogComponent,
        DojoClubDeleteDialogComponent,
        DojoClubPopupComponent,
        DojoClubDeletePopupComponent,
    ],
    entryComponents: [
        DojoClubComponent,
        DojoClubDialogComponent,
        DojoClubPopupComponent,
        DojoClubDeleteDialogComponent,
        DojoClubDeletePopupComponent,
    ],
    providers: [
        DojoClubService,
        DojoClubPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FsjdaDojoClubModule {}
