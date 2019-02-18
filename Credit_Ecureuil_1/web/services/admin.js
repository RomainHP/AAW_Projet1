(function () {
    'use strict';
 
    angular
        .module('app')
        .factory('AdminService', AdminService);
 
    AdminService.$inject = ['$http', '$q'];
    
    function AdminService($http, $q) {
        
        var service = {};
        service.consultation = consultation;
        service.virement = virement;
        service.ajout_livret = ajout_livret;
        return service;
        
        function consultation(){
            var deferred = $q.defer();
            $http({
                url: 'http://localhost:8080/Credit_Ecureuil_1/consultation_admin.htm',
                method: "GET"
            }
            ).then(
                function(response){
                    deferred.resolve(response.data);
                },
                function(errResponse){
                    deferred.reject(errResponse);
                }
            );
            return deferred.promise;
        }
        
        function virement(source, dest, montant){
            var deferred = $q.defer();
            $http({
                url: 'http://localhost:8080/Credit_Ecureuil_1/virement_admin.htm',
                method: "POST",
                params: {
                    'source' : source,
                    'dest' : dest,
                    'montant' : montant
                }
            }
            ).then(
                function(response){
                    deferred.resolve(response.data);
                },
                function(errResponse){
                    deferred.reject(errResponse);
                }
            );
            return deferred.promise;
        }
        
        function ajout_livret(nomCpt, utilisateur){
            var deferred = $q.defer();
            $http({
                url: 'http://localhost:8080/Credit_Ecureuil_1/ajout_livret_admin.htm',
                method: "POST",
                params: {
                    'nom_compte' : nomCpt,
                    'utilisateur' : utilisateur
                }
            }
            ).then(
                function(response){
                    deferred.resolve(response.data);
                },
                function(errResponse){
                    deferred.reject(errResponse);
                }
            );
            return deferred.promise;
        }
    }
 
})();
 