using { cuid } from '@sap/cds/common';


service bookshop{

    //managed association - when rule will be manged by CAPM
    entity Books: cuid {
        title : String;
        author : Association to Authors;
    }

    entity Authors: cuid {
        name : String;
    }

    //unmanged association - when we are going to define the Rule

}




























//--------------------Commented area-----------------------------//

//Custom aspect
// aspect cuid{
//     key ID: UUID;
// }


// using { cuid, managed } from '@sap/cds/common';

// service bookshop{
//     entity Books: cuid, managed {
//         title : String;
//     }
// }