(function () {
    'use strict';
 
    angular
        .module('app')
        .factory('CompteService', CompteService);
 
    CompteService.$inject = ['$http', '$q', '$rootScope'];
    
    function CompteService($http, $q) {
        
        var service = {};
        service.consultation = consultation;
        service.createAccount = createAccount;
        service.getAllAccounts = getAllAccounts;
        service.virement = virement;
        service.createLinkedAccount = createLinkedAccount;
        service.details = details;
        service.deleteAccount = deleteAccount;
        
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
                    deferred.resolve(response.data);
                },
                function(errResponse){
                    deferred.reject(errResponse);
                }
            );
            return deferred.promise;
        }
        
        function createAccount(name, user){
            var deferred = $q.defer();
            $http({
                url: 'http://localhost:8080/Credit_Ecureuil_1/ajout_livret.htm',
                method: "POST",
                params: {
                        'name': name,
                        'login': user
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
        
        function getAllAccounts(){
            var deferred = $q.defer();
            $http({
                url: 'http://localhost:8080/Credit_Ecureuil_1/virement.htm',
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
                url: 'http://localhost:8080/Credit_Ecureuil_1/virement.htm',
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
        
        function createLinkedAccount(name, mail, listeCoProprietaire){
            var deferred = $q.defer();
            $http({
                url: 'http://localhost:8080/Credit_Ecureuil_1/create_linked_account.htm',
                method: "POST",
                data : {
                    'login' : mail,
                    'nom_compte' : name,
                    'listeCoPro' : listeCoProprietaire
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
        
        function details(login, idCompte){
            var deferred = $q.defer();
            $http({
                url: 'http://localhost:8080/Credit_Ecureuil_1/details.htm',
                method: "GET",
                params : {'login' : login,
                          'idCompte' : idCompte
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
        
        function deleteAccount(id){
            var deferred = $q.defer();
            $http({
                url: 'http://localhost:8080/Credit_Ecureuil_1/supprimer_livret.htm',
                method: "POST",
                params : {
                    'id' : id
                }
            }
            ).then(
                function(response){
                    deferred.resolve(response.data);
                },
                function(errResponse){
                    console.log(errResponse);
                    deferred.reject(errResponse);
                }
            );
            return deferred.promise;
        }
    }
})();