using SalesOrderService as service from '../../srv/SalesOrderService';
using from '../../db/SalesOrder';

annotate service.SalesOrderHeader with @(
    UI.FieldGroup #GeneratedGroup : {
        $Type : 'UI.FieldGroupType',
        Data : [
            {
                $Type : 'UI.DataField',
                Label : 'Sales Order Desc',
                Value : SalesOrderDesc,
            },
            {
                $Type : 'UI.DataField',
                Label : 'Customer',
                Value : Customer,
            },
            {
                $Type : 'UI.DataField',
                Label : 'Customer No',
                Value : CustomerNo,
            },
            
        ],
    },
    UI.FieldGroup #GeneratedGroup2 : {
        $Type : 'UI.FieldGroupType',
        Data : [
            {
                $Type : 'UI.DataField',
                Label : 'Sales Order Date',
                Value : SalesOrderDate,
            },
            {
                $Type : 'UI.DataField',
                Label : 'Sales Order Total',
                Value : SalesOrderTotal,
            },
        ],
    },
    UI.Facets : [
        {
            $Type : 'UI.ReferenceFacet',
            ID : 'GeneratedFacet1',
            Label : 'Header',
            Target : '@UI.FieldGroup#GeneratedGroup',
        },
        {
            $Type : 'UI.ReferenceFacet',
            ID : 'GeneratedFacet2',
            Label : 'Details',
            Target : '@UI.FieldGroup#GeneratedGroup2',
        },
        {
            $Type : 'UI.ReferenceFacet',
            Label : 'Sales Order Items',
            ID : 'SalesOrderItems',
            Target : 'SOItems/@UI.LineItem#SalesOrderItems',
        },
    ],
    UI.LineItem : [
        {
            $Type : 'UI.DataField',
            Label : 'Sales Order ID',
            Value : ID,
        },
        {
            $Type : 'UI.DataField',
            Label : 'Sales Order Description',
            Value : SalesOrderDesc,
        },
        {
            $Type : 'UI.DataField',
            Label : 'Customer',
            Value : Customer,
        },
        {
            $Type : 'UI.DataField',
            Label : 'Customer No',
            Value : CustomerNo,
        },
        {
            $Type : 'UI.DataField',
            Label : 'Sales Order Date',
            Value : SalesOrderDate,
        },
        {
            $Type : 'UI.DataField',
            Label : 'Sales Order Total',
            Value : SalesOrderTotal,
        },
    ],
);

annotate service.SalesOrderItems with @(
    UI.LineItem #SalesOrderItems : [
        {
            $Type : 'UI.DataField',
            Value : parent.SOItems.ProductId,
            Label : 'Product Id',
        },
        {
            $Type : 'UI.DataField',
            Value : parent.SOItems.ProductName,
            Label : 'Product Name',
        },
        {
            $Type : 'UI.DataField',
            Value : parent.SOItems.Quantity,
            Label : 'Quantity',
        },
        {
            $Type : 'UI.DataField',
            Value : parent.SOItems.UnitPrice,
            Label : 'Unit Price',
        },
        {
            $Type : 'UI.DataField',
            Value : parent.SOItems.UnitTotal,
            Label : 'Unit Total',
        },
    ]
);

