import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FsjdaSharedModule } from '../../shared';
import {
    TarifCeintureService,
    TarifCeinturePopupService,
    TarifCeintureComponent,
    TarifCeintureDetailComponent,
    TarifCeintureDialogComponent,
    TarifCeinturePopupComponent,
    TarifCeintureDeletePopupComponent,
    TarifCeintureDeleteDialogComponent,
    tarifCeintureRoute,
    tarifCeinturePopupRoute,
} from './';

const ENTITY_STATES = [
    ...tarifCeintureRoute,
    ...tarifCeinturePopupRoute,
];

@NgModule({
    imports: [
        FsjdaSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        TarifCeintureComponent,
        TarifCeintureDetailComponent,
        TarifCeintureDialogComponent,
        TarifCeintureDeleteDialogComponent,
        TarifCeinturePopupComponent,
        TarifCeintureDeletePopupComponent,
    ],
    entryComponents: [
        TarifCeintureComponent,
        TarifCeintureDialogComponent,
        TarifCeinturePopupComponent,
        TarifCeintureDeleteDialogComponent,
        TarifCeintureDeletePopupComponent,
    ],
    providers: [
        TarifCeintureService,
        TarifCeinturePopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FsjdaTarifCeintureModule {}
