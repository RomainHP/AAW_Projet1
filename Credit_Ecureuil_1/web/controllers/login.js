(function () {
    'use strict';
 
    angular
        .module('app')
        .controller('LoginController', LoginController);
 
    LoginController.$inject = ['$location', 'AuthenticationService', 'FlashService'];
    
    function LoginController($location, AuthenticationService, FlashService) {
        var vm = this;
 
        vm.login = login;
 
        initController();
        
        function initController() {
            AuthenticationService.ClearCredentials();
        }
 
        function login() {
            AuthenticationService.Login(vm.username, vm.password, function (response) {
                if (response.success) {
                    AuthenticationService.SetCredentials(vm.username, vm.password);
                    FlashService.Success('Utilisateur connecté', true);
                    $location.path('/');
                } else {
                    FlashService.Error(response.message);
                }
            });
        };
    }
 
})();


