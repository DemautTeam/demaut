<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet"%>

<portlet:defineObjects />

<portlet:actionURL name="uploadAnnexe" var="uploadAnnexeAction" />

<!-- <form action="${uploadAnnexeAction}" enctype="multipart/form-data"
	method="post">
	<div>
		Votre annexe <input type="file" name="annexeFile" /> <br>
		<button type="submit">Envoyer le fichier</button>
	</div>
</form> -->
<portlet:resourceURL var="ajaxCallResourceURL" />
<div ng-controller="uploadAnnexeController">
	<ng-form name="uploadForm"  ng-init="uploadUrl='${ajaxCallResourceURL}'">
		<div>{{message}}</div>
		<input
			type="file" name="annexeFile" ng-model="annexeFile" 
			onchange="angular.element(this).scope().fileChanged(this)"/> <br>
	<button ng-click="ajaxUpload(uploadForm)">Envoyer le fichier</button>
	</ng-form>
	<div>{{annexeFile[0].name}} - Taille {{annexeFile[0].size}}</div>
</div>
<div ng-controller="progreSOATiersController" ng-init="uploadUrl='${ajaxCallResourceURL}'">
	<input type="text" ng-change="queryChange()" ng-model="query">
	<div>{{query}}</div>
	<div ng-show="tiers.moralPerson.length > 0">
		<p>Il y a {{tiers.moralPerson.length}} résultats</p>
		<ul>
			<li ng-repeat="tier in tiers.moralPerson">
				{{tier.organisation}}
			</li>
		</ul>
	</div>
	
</div>