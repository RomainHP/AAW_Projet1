(function () {
    'use strict';
 
    angular
        .module('app')
        .controller('RegisterProController', RegisterProController);
 
    RegisterProController.$inject = ['rootScope', 'UtilisateurService', '$location', 'FlashService'];
    function RegisterProController($rootScope, UtilisateurService, $location, FlashService) {
        var vm = this;
  
        vm.registerpro = registerpro;
        vm.user = null;
        
        initController($rootScope);

        function initController($rootScope) {
           vm.user = $rootScope.globals.currentUser;
        }
                
        function registerpro() {
            vm.dataLoading = true;
            UtilisateurService.registerpro(vm.userTmp.email,vm.userTmp.entreprise, vm.userTmp.siret, vm.userTmp.password)
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

