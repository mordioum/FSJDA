import { BaseEntity, User } from './../../shared';

export class Ligue implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public president?: string,
        public dateCreation?: any,
        public description?: string,
        public adresse?: string,
        public telephone?: string,
        public email?: string,
        public dojoclubs?: BaseEntity[],
        public user?: User,
        public discipline?: BaseEntity,
    ) {
    }
}
