import { BaseEntity } from './../../shared';

export class Discipline implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public dateCreation?: any,
        public description?: string,
        public ligues?: BaseEntity[],
    ) {
    }
}
