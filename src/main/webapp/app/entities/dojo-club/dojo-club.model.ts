import { BaseEntity, User } from './../../shared';

export class DojoClub implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public president?: string,
        public dateCreation?: any,
        public description?: string,
        public adresse?: string,
        public telephone?: string,
        public email?: string,
        public athletes?: BaseEntity[],
        public user?: User,
        public ligue?: BaseEntity,
    ) {
    }
}
