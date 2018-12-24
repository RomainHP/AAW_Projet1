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
            AuthentificationService.login(vm.user.email, vm.user.password)
                .then(function () {
                    AuthenticationService.setCredentials(vm.username, vm.password);
                    FlashService.Success('Utilisateur connecté', true);
                    $location.path('/');
                },
                function (errResponse) {
                    FlashService.Error(errResponse.data);
                }
            );
        };
    }
 
})();


