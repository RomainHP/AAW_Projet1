(function () {
    'use strict';
 
    angular
        .module('app')
        .controller('RegisterController', RegisterController);
 
    RegisterController.$inject = ['UserService', '$location', 'FlashService'];
    function RegisterController(UserService, $location, FlashService) {
        var vm = this;
 
        vm.register = register;
 
        
        function register() {
            vm.dataLoading = true;
            UserService.Create(vm.user)
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

