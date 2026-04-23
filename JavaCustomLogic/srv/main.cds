using org.techyks as api from '../db/schema';

extend api.Books with {
    virtual category : String;
}

service BookshopService {

    //entity Books as projection on api.Books;

    entity Books as projection on api.Books
        actions {
            //bound Function
            function stockValue() returns Integer;
            //bound action
            action setPrice(price : Integer) returns Books;
        };


    //unbound Function
    function getMeSomething() returns String;

    //unbound Function
    function totalStock() returns Integer;

    //unbound action
    action submitBookOrder(id: UUID, stock : Integer) returns Integer;
}