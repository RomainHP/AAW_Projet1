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
            AuthentificationService.clearCredentials();
        }
 
        function login() {
            vm.dataLoading = true;
            AuthentificationService.login(vm.user.email, vm.user.password)
                .then(function (response) {
                    AuthentificationService.setCredentials(vm.user.email, vm.user.password, response.data["isPro"], response.data["isAdmin"]);
                    FlashService.Success('Utilisateur connecté', true);
                    $location.path('/');
                },
                function (errResponse) {
                    FlashService.Error("Erreur : " + errResponse.data["errorMessage"], true);
                    $location.path('/');
                }
            );
        };
    }
 
})();


