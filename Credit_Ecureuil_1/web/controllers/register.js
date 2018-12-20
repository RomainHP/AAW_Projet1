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
            UtilisateurService.register(vm.user.email.toString(), vm.user.password.toString())
                    .then(function () {
                        FlashService.Success('Utilisateur enregistré avec succès', true);
                        $location.path('/login');
                    },
                    function (errResponse) {
                        FlashService.Error('Utilisateur déja enregistré');
                    }
                );
        }
    }
 
})();

