import { BaseEntity } from './../../shared';

export class TarifCeinture implements BaseEntity {
    constructor(
        public id?: number,
        public ceinture?: string,
        public dateCreation?: any,
        public montant?: number,
        public licences?: BaseEntity[],
    ) {
    }
}
