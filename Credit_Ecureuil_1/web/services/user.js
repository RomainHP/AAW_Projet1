(function () {
    'use strict';
 
    angular
        .module('app')
        .factory('UserService', UserService);
 
    UserService.$inject = ['$http', '$q'];
    
    function UserService($http, $q) {
        
        var service = {};
        
        return service;
    }
 
})();
 