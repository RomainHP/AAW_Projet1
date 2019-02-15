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
 
        function getMessages(user){
            var deferred = $q.defer();
            $http({
                url: 'http://localhost:8084/Credit_Ecureuil_1/consulter_messagerie.htm',
                method: "GET",
                params: 
                    {
                        'user': user
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
        
        function sendMessage(from, to, sujet, message) {
            var deferred = $q.defer();
            $http({
                url: 'http://localhost:8084/Credit_Ecureuil_1/envoyer_message.htm',
                method: "POST",
                params: 
                    {
                        'from': from,
                        'to': to,
                        'sujet': sujet,
                        'message': message
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
        
        function removeMessage(id) {
            var deferred = $q.defer();
            $http({
                url: 'http://localhost:8084/Credit_Ecureuil_1/supprimer_message.htm',
                method: "POST",
                params: 
                    {
                        "id": id
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