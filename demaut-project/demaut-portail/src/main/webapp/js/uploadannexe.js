(function(Liferay, angular) {
	angular.portlet.add("demautportail", "annexe-upload",
		function() {
			var uploadModule = angular.module("uploadModule", []);
			uploadModule.service('fileUpload', ['$http', '$log', function ($http, $log) {
			    this.uploadFileToUrl = function(file, uploadUrl){
			    	var formData = new FormData();
			    	formData.append('ajaxAction', 'upload');
			    	formData.append("annexeFile", file);
			    	formData.append("annexeFileName", file.name);
			    	formData.append("annexeFileSize", file.size);
			    	formData.append("annexeFileType", file.type);
			    	$http.post(uploadUrl + '&ajaxAction=upload', formData, {
			    		transformRequest: angular.identity,
			            headers: {'Content-Type': undefined}
			    		
			        })
			        .success(function(d){
			        	$log.info(d);
			        	$log.info('success');
			        })
			        .error(function(e){
			        	$log.info(e);
			        	$log.error('Not Ok !');
			        });
			    	
			    	
			    	
			    	/*var annexeFile = {};
			    	annexeFile.name = file.name;
			    	annexeFile.size = file.size;
			    	annexeFile.type = file.type;
			    	var reader = new FileReader();
			    	reader.onloadend = function(event){
			    		annexeFile.file = reader.result;
			    		annexeFile.file = annexeFile.file.replace(/.*base64,/i, '');
			    		$log.info("longueur de l'objet file " + annexeFile.file.length);
			    		if(annexeFile.file.length > 4000){
			    		   		annexeFile.file = '';
			    		}
			    		var fd = new FormData();
			    		
				    	fd.append("annexeFile", annexeFile);
				    	
				        $http.post(uploadUrl, annexeFile, {
				            headers: {'Content-Type': "application/json"}
				        })
				        .success(function(d){
				        	$log.info(d);
				        	$log.info('success');
				        })
				        .error(function(e){
				        	$log.info(e);
				        	$log.error('Not Ok !');
				        });
			    	}
			    	reader.readAsDataURL(file);*/
			    	
			    	
			    }
			}]);
			uploadModule.controller("uploadAnnexeController", ['$scope', '$log', 'fileUpload', function($scope, $log, fileUpload) {
				$scope.message = 'Selectionner l\'annexe à joindre à votre dossier';
				
				$scope.fileChanged = function(element){
					$log.info('entry changed !');
					$scope.annexeFile = element.files;
					$scope.$apply();
					$log.info($scope.annexeFile[0].name + " - Stringified version : " + JSON.stringify($scope.annexeFile));
				}
				
				$scope.ajaxUpload = function(uploadForm){
					$log.info('Uploading...');
					var annexeFile = $scope.annexeFile[0];
					console.log('file is ' + JSON.stringify(annexeFile));
					var uploadUrl = $scope.uploadUrl;
					console.log('uploadURL is ' + JSON.stringify(uploadUrl));
					fileUpload.uploadFileToUrl(annexeFile, uploadUrl);
				}
				
				
			}]);
			uploadModule.controller("progreSOATiersController", ['$scope', '$log', '$http', '$timeout',  function($scope, $log, $http, $timeout) {
				$scope.queryInProcess = null;
				$scope.tiers = [];
				
				$scope.queryChange = function(){
					$log.info($scope.query);
					if($scope.query != '' && $scope.queryInProcess == null){
						$scope.queryInProcess = $timeout(function(){
							$http.get($scope.uploadUrl + '&nom=' + $scope.query + '&ajaxAction=progreSOATiers'
								).then(function(response){
									$scope.queryInProcess = null;
									$log.info('Response' + response.data + ' ' + response.length);
									$scope.tiers = response.data;
								});
							},1000);
					}
				};
				
			}] )
			return [ uploadModule.name ];
		});
	console.log("AngularJS loaded !");
})(Liferay, angular);
