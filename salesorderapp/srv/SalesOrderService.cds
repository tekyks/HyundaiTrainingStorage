using hyundai.api as api from '../db/SalesOrder';

service SalesOrderService {
    entity SalesOrderHeader as projection on api.SalesOrder;
    annotate SalesOrderHeader with @odata.draft.enabled;


    //entity SalesOrderItems as projection on api.SalesOrderItems;
}