if(!angularPortlets){
	console.log("Construction des référence des portlets")
	var angularPortlets = new Array();
	
}

(function(Liferay, angular) {
	if (angular.portlet)
		return;
	console.log("Première instance de angular");
	angular.portlet = {};


	angular.portlet.add = function(pluginName, portletName, angularFunction) {
		var portletId = "_WAR_" + pluginName.replace(/[_]|[-]/g, "");
		portletId = portletName.replace(/[_]|[-]/g, "") + portletId;
		angularPortlets[portletId] = angularFunction;
		console.log(portletId + ' enregistré ' +  angularPortlets.length);
	};

	Liferay.Portlet.ready(function(portletInstanceId, node) {
		var portletId = portletInstanceId.replace(/[_]INSTANCE[_].+/g, "");
		console.log('tentative de démarrage de angular pour ' + portletInstanceId);

		if (angularPortlets[portletId]) {
			angular.bootstrap(node.getDOMNode(), angularPortlets[portletId](
				portletInstanceId, node.getDOMNode()));
			console.log('démarrage de angular pour ' + portletInstanceId);
		}
	});
})(Liferay, angular);