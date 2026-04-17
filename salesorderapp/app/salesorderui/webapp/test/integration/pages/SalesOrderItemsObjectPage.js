sap.ui.define(['sap/fe/test/ObjectPage'], function(ObjectPage) {
    'use strict';

    var CustomPageDefinitions = {
        actions: {},
        assertions: {}
    };

    return new ObjectPage(
        {
            appId: 'hyundai.ui.salesorderui',
            componentId: 'SalesOrderItemsObjectPage',
            contextPath: '/SalesOrderHeader/SOItems'
        },
        CustomPageDefinitions
    );
});