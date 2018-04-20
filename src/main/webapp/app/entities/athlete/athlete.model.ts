import { BaseEntity } from './../../shared';

export const enum Sexe {
    'M',
    'F'
}

export class Athlete implements BaseEntity {
    constructor(
        public id?: number,
        public nom?: string,
        public prenom?: string,
        public dateCreation?: any,
        public sexe?: Sexe,
        public dateNaissance?: any,
        public lieuNaissance?: string,
        public adresse?: string,
        public telephone?: string,
        public email?: string,
        public licences?: BaseEntity[],
        public dojoclub?: BaseEntity,
    ) {
    }
}
