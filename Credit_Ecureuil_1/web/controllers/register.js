(function () {
    'use strict';
 
    angular
        .module('app')
        .controller('RegisterController', RegisterController);
 
    RegisterController.$inject = ['UtilisateurService', '$location', 'FlashService'];
    function RegisterController(UtilisateurService, $location, FlashService) {
        var vm = this;
  
        vm.register = register;
                
        function register() {
            vm.dataLoading = true;
            UtilisateurService.register(vm.user.email, vm.user.password)
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

