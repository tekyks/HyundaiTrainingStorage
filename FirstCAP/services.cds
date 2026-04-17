using {cuid} from '@sap/cds/common';


service bookshop {

    //managed association - when rule will be manged by CAPM
    //1 - 1 relationship
    entity Books: cuid {
        title : String;
        author : Association to Authors;
    }

    //unmanged association - when we are going to define the Rule
    // entity Books : cuid {
    //     title             : String;
    //     author_foreignKey : type of Authors : ID;
    //     author            : Association to Authors
    //                             on author.ID = author_foreignKey;
    // }

    // entity Authors : cuid {
    //     name : String;
    // }

    //relationship -> to many 
    entity Authors : cuid {
        name : String;
        book : Association to many Books
                   on book.author = $self;
    }

    //Composition
    entity Orders : cuid {
        customer : String;
        Items    : Composition of many OrderItems
                       on Items.parent = $self;
    }

    entity OrderItems {
        key parent   : Association to Orders;
        key position : Integer;
            quantity : Integer;
    }


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