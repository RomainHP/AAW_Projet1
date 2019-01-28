(function () {
    'use strict';
 
    angular
        .module('app')
        .controller('ConsultationController', ConsultationController);
 
    ConsultationController.$inject = ['UtilisateurService', '$location', 'FlashService'];
    function ConsultationController(UtilisateurService, $location, FlashService) {
        var vm = this;
  
        vm.consult = consult;
                
        function consult() {
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

