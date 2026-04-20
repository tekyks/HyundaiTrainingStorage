namespace techyks.api;

using {cuid} from '@sap/cds/common';

entity Books: cuid {
    title: String;
}

entity Authors: cuid {
    name: String;
}

entity ConfidentialData: cuid {
    desc: String;
}