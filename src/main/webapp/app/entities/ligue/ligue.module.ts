import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FsjdaSharedModule } from '../../shared';
import { FsjdaAdminModule } from '../../admin/admin.module';
import {
    LigueService,
    LiguePopupService,
    LigueComponent,
    LigueDetailComponent,
    LigueDialogComponent,
    LiguePopupComponent,
    LigueDeletePopupComponent,
    LigueDeleteDialogComponent,
    ligueRoute,
    liguePopupRoute,
} from './';

const ENTITY_STATES = [
    ...ligueRoute,
    ...liguePopupRoute,
];

@NgModule({
    imports: [
        FsjdaSharedModule,
        FsjdaAdminModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        LigueComponent,
        LigueDetailComponent,
        LigueDialogComponent,
        LigueDeleteDialogComponent,
        LiguePopupComponent,
        LigueDeletePopupComponent,
    ],
    entryComponents: [
        LigueComponent,
        LigueDialogComponent,
        LiguePopupComponent,
        LigueDeleteDialogComponent,
        LigueDeletePopupComponent,
    ],
    providers: [
        LigueService,
        LiguePopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FsjdaLigueModule {}
