(function () {
    'use strict';
 
    angular
        .module('app')
        .controller('DisconnectController', DisconnectController);
 
    DisconnectController.$inject = ['$location', 'AuthentificationService', 'FlashService'];
    
    function DisconnectController($location, AuthentificationService, FlashService) {
        AuthentificationService.clearCredentials();
        FlashService.Success('Utilisateur deconnecte', true);
        $location.path('/');
    }
 
})();


