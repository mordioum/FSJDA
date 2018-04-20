import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FsjdaSharedModule } from '../../shared';
import {
    AthleteService,
    AthletePopupService,
    AthleteComponent,
    AthleteDetailComponent,
    AthleteDialogComponent,
    AthletePopupComponent,
    AthleteDeletePopupComponent,
    AthleteDeleteDialogComponent,
    athleteRoute,
    athletePopupRoute,
} from './';

const ENTITY_STATES = [
    ...athleteRoute,
    ...athletePopupRoute,
];

@NgModule({
    imports: [
        FsjdaSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        AthleteComponent,
        AthleteDetailComponent,
        AthleteDialogComponent,
        AthleteDeleteDialogComponent,
        AthletePopupComponent,
        AthleteDeletePopupComponent,
    ],
    entryComponents: [
        AthleteComponent,
        AthleteDialogComponent,
        AthletePopupComponent,
        AthleteDeleteDialogComponent,
        AthleteDeletePopupComponent,
    ],
    providers: [
        AthleteService,
        AthletePopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FsjdaAthleteModule {}
