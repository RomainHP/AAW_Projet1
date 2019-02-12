(function () {
    'use strict';
 
    angular
        .module('app')
        .controller('LoginController', LoginController);
 
    LoginController.$inject = ['rootScope', '$location', 'AuthentificationService', 'FlashService'];
    
    function LoginController($rootScope, $location, AuthentificationService, FlashService) {
        var vm = this;
        
        vm.login = login;
        vm.user = null;
 
        initController($rootScope);
        
        function initController($rootScope) {
            vm.user = $rootScope.globals.currentUser;
            AuthentificationService.clearCredentials();
        }
 
        function login() {
            vm.dataLoading = true;
            AuthentificationService.login(vm.userTmp.email, vm.userTmp.password)
                .then(function (response) {
                    AuthentificationService.setCredentials(vm.userTmp.email, vm.userTmp.password, response.data["isPro"], response.data["isAdmin"]);
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


