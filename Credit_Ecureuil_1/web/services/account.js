(function () {
    'use strict';
 
    angular
        .module('app')
        .factory('CompteService', CompteService);
 
    CompteService.$inject = ['$http', '$q', '$rootScope'];
    
    function CompteService($http, $q) {
        
        var service = {};
        service.consultation = consultation;
        
        return service;
    
        function consultation(mail){
            var deferred = $q.defer();
            $http({
                url: 'http://localhost:8080/Credit_Ecureuil_1/consultation.htm',
                method: "GET",
                params : {mail : mail}
            }
            ).then(
                function(response){
                    console.log(response.data);
                    deferred.resolve(response.data);
                },
                function(errResponse){
                    console.log("erreur recuperation compte");
                    deferred.reject(errResponse);
                }
            );
            return deferred.promise;
        }
    }
})();