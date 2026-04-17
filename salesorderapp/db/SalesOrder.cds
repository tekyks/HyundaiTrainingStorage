namespace hyundai.api;

aspect cuid {
    key ID : UUID;
}

entity SalesOrder : cuid {
    SalesOrderDesc  : String;
    Customer        : String;
    CustomerNo      : String;
    SalesOrderDate  : Date;
    SalesOrderTotal : Decimal;
    SOItems         : Composition of many SalesOrderItems
                          on SOItems.parent = $self;
}

entity SalesOrderItems : cuid {
    key parent      : Association to one SalesOrder;
        ProductId   : String;
        ProductName : String;
        UnitPrice   : Decimal;
        Quantity    : Integer;
        UnitTotal   : Decimal;
}