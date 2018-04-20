import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { FsjdaDisciplineModule } from './discipline/discipline.module';
import { FsjdaLigueModule } from './ligue/ligue.module';
import { FsjdaDojoClubModule } from './dojo-club/dojo-club.module';
import { FsjdaAthleteModule } from './athlete/athlete.module';
import { FsjdaTarifCeintureModule } from './tarif-ceinture/tarif-ceinture.module';
import { FsjdaLicenceModule } from './licence/licence.module';
import { FsjdaSaisonModule } from './saison/saison.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        FsjdaDisciplineModule,
        FsjdaLigueModule,
        FsjdaDojoClubModule,
        FsjdaAthleteModule,
        FsjdaTarifCeintureModule,
        FsjdaLicenceModule,
        FsjdaSaisonModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FsjdaEntityModule {}
