namespace techyks.api;

using {cuid, managed} from '@sap/cds/common';

entity Books: cuid, managed {
    title: String;
}

entity Authors: cuid, managed {
    name: String;
}

entity ConfidentialData: cuid {
    desc: String;
}