import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FsjdaSharedModule } from '../../shared';
import {
    SaisonService,
    SaisonPopupService,
    SaisonComponent,
    SaisonDetailComponent,
    SaisonDialogComponent,
    SaisonPopupComponent,
    SaisonDeletePopupComponent,
    SaisonDeleteDialogComponent,
    saisonRoute,
    saisonPopupRoute,
} from './';

const ENTITY_STATES = [
    ...saisonRoute,
    ...saisonPopupRoute,
];

@NgModule({
    imports: [
        FsjdaSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        SaisonComponent,
        SaisonDetailComponent,
        SaisonDialogComponent,
        SaisonDeleteDialogComponent,
        SaisonPopupComponent,
        SaisonDeletePopupComponent,
    ],
    entryComponents: [
        SaisonComponent,
        SaisonDialogComponent,
        SaisonPopupComponent,
        SaisonDeleteDialogComponent,
        SaisonDeletePopupComponent,
    ],
    providers: [
        SaisonService,
        SaisonPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FsjdaSaisonModule {}
