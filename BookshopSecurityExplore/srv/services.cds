using techyks.api as api from '../db/schema';

service Bookshop {

    @(restrict: [
        {
            grant: '*',
            to   : 'Administrator'
        },
        {
            grant: '*',
            where: 'createdBy = $user'
        }
    ])
    entity Books   as projection on api.Books;

    @(restrict: [
        {
            grant: [
                'CREATE',
                'UPDATE',
                'DELETED'
            ],
            to   : 'Administrator'
        },
        {
            grant: 'READ',
            to   : [
                'Administrator',
                'authenticated-user'
            ]
        }

    ])
    entity Authors as projection on api.Authors;
}

service AdminServive {
    entity ConfidentialData as projection on api.ConfidentialData;
}

annotate AdminServive @(requires: 'Administrator');