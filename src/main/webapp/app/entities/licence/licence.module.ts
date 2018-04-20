import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FsjdaSharedModule } from '../../shared';
import {
    LicenceService,
    LicencePopupService,
    LicenceComponent,
    LicenceDetailComponent,
    LicenceDialogComponent,
    LicencePopupComponent,
    LicenceDeletePopupComponent,
    LicenceDeleteDialogComponent,
    licenceRoute,
    licencePopupRoute,
} from './';

const ENTITY_STATES = [
    ...licenceRoute,
    ...licencePopupRoute,
];

@NgModule({
    imports: [
        FsjdaSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        LicenceComponent,
        LicenceDetailComponent,
        LicenceDialogComponent,
        LicenceDeleteDialogComponent,
        LicencePopupComponent,
        LicenceDeletePopupComponent,
    ],
    entryComponents: [
        LicenceComponent,
        LicenceDialogComponent,
        LicencePopupComponent,
        LicenceDeleteDialogComponent,
        LicenceDeletePopupComponent,
    ],
    providers: [
        LicenceService,
        LicencePopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FsjdaLicenceModule {}
