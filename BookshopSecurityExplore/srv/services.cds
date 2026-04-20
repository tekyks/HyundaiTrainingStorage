using techyks.api as api from '../db/schema';

service Bookshop {
    entity Books as projection on api.Books;

    entity Authors as projection on api.Authors;
}

service AdminServive{
    entity ConfidentialData as projection on api.ConfidentialData;
}

annotate AdminServive @(requires: 'Administrator');