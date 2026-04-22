namespace org.techyks;

using {cuid} from '@sap/cds/common';


entity Books : cuid {
    title : String;
    stock : Integer;
    price : Integer;
}