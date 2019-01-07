(function () {
    'use strict';
 
    angular
        .module('app')
        .controller('RegisterProController', RegisterProController);
 
    RegisterProController.$inject = ['UtilisateurService', '$location', 'FlashService'];
    function RegisterProController(UtilisateurService, $location, FlashService) {
        var vm = this;
  
        vm.registerpro = registerpro;
                
        function registerpro() {
            vm.dataLoading = true;
            UtilisateurService.registerpro(vm.user.email,vm.user.entreprise, vm.user.siret, vm.user.password)
                .then(function () {
                    FlashService.Success('Utilisateur enregistré avec succès', true);
                    $location.path('/');
                },
                function (errResponse) {
                    FlashService.Error(errResponse.data);
                }
            );
        }
    }
 
})();

