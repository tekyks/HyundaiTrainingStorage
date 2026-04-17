sap.ui.define([
    "sap/fe/test/JourneyRunner",
	"hyundai/ui/salesorderui/test/integration/pages/SalesOrderHeaderList",
	"hyundai/ui/salesorderui/test/integration/pages/SalesOrderHeaderObjectPage",
	"hyundai/ui/salesorderui/test/integration/pages/SalesOrderItemsObjectPage"
], function (JourneyRunner, SalesOrderHeaderList, SalesOrderHeaderObjectPage, SalesOrderItemsObjectPage) {
    'use strict';

    var runner = new JourneyRunner({
        launchUrl: sap.ui.require.toUrl('hyundai/ui/salesorderui') + '/test/flpSandbox.html#hyundaiuisalesorderui-tile',
        pages: {
			onTheSalesOrderHeaderList: SalesOrderHeaderList,
			onTheSalesOrderHeaderObjectPage: SalesOrderHeaderObjectPage,
			onTheSalesOrderItemsObjectPage: SalesOrderItemsObjectPage
        },
        async: true
    });

    return runner;
});

