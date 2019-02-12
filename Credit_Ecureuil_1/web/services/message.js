(function () {
    'use strict';
 
    angular
        .module('app')
        .factory('MessageService', MessageService);
 
    MessageService.$inject = ['$http', '$q', '$cookies', '$rootScope'];
    function MessageService($http, $q) {
        var service = {};
 
        service.getMessages = getMessages;
        service.sendMessage = sendMessage;
        service.removeMessage = removeMessage;
 
        return service;
 
        function getMessages(){
            var deferred = $q.defer();
            $http({
                url: 'http://localhost:8080/Credit_Ecureuil_1/consulter_messagerie.htm',
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
        
        function sendMessage() {
            
        }
        
        function removeMessage() {
            
        }
    }
 
})();