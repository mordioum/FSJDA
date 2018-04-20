import { BaseEntity } from './../../shared';

export const enum TypeLicence {
    'RENOUVELLEMENT',
    'NOUVELLE'
}

export class Licence implements BaseEntity {
    constructor(
        public id?: number,
        public typeLicence?: TypeLicence,
        public dateCreation?: any,
        public photoContentType?: string,
        public photo?: any,
        public certificatMedicalContentType?: string,
        public certificatMedical?: any,
        public athlete?: BaseEntity,
        public tarifceinture?: BaseEntity,
        public saison?: BaseEntity,
    ) {
    }
}
