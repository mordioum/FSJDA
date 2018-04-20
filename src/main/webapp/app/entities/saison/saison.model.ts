import { BaseEntity } from './../../shared';

export class Saison implements BaseEntity {
    constructor(
        public id?: number,
        public saison?: number,
        public dateCreation?: any,
        public licences?: BaseEntity[],
    ) {
    }
}
