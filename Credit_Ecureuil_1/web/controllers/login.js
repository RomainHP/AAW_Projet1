(function () {
    'use strict';
 
    angular
        .module('app')
        .controller('LoginController', LoginController);
 
    LoginController.$inject = ['$location', 'AuthentificationService', 'FlashService'];
    
    function LoginController($location, AuthentificationService, FlashService) {
        var vm = this;
 
        vm.login = login;
 
        initController();
        
        function initController() {
            AuthentificationService.ClearCredentials();
        }
 
        function login() {
            AuthentificationService.Login(vm.username, vm.password, function (response) {
                if (response.success) {
                    AuthentificationService.SetCredentials(vm.username, vm.password);
                    FlashService.Success('Utilisateur connecté', true);
                    $location.path('/');
                } else {
                    FlashService.Error(response.message);
                }
            });
        };
    }
 
})();


