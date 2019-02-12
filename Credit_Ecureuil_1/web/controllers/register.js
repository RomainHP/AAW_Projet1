(function () {
    'use strict';
 
    angular
        .module('app')
        .controller('RegisterController', RegisterController);
 
    RegisterController.$inject = ['rootScope', 'UtilisateurService', '$location', 'FlashService'];
    function RegisterController($rootScope, UtilisateurService, $location, FlashService) {
        var vm = this;
  
        vm.user = null;
        vm.register = register;
        
        initController($rootScope);

        function initController($rootScope) {
           vm.user = $rootScope.globals.currentUser;
        }
                
        function register() {
            vm.dataLoading = true;
            UtilisateurService.register(vm.userTmp.email, vm.userTmp.password)
                .then(function () {
                    FlashService.Success('Utilisateur enregistré avec succès', true);
                    $location.path('/');
                },
                function (errResponse) {
                    FlashService.Error("Erreur : " + errResponse.data["errorMessage"], true);
                    $location.path('/');
                }
            );
        }
    }
 
})();

