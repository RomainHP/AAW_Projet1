(function () {
    'use strict';

    angular
        .module('app')
        .controller('ProfileController', ProfileController);

    ProfileController.$inject = ['$rootScope', 'UtilisateurService', 'FlashService'];
    function ProfileController($rootScope, UtilisateurService, FlashService) {
        
        var vm = this;
        
        vm.user = null;
        
        vm.updateProfile = updateProfile;
        vm.fillFields = fillFields;
        
        initController($rootScope);

        function initController($rootScope) {
           UtilisateurService.getProfile($rootScope.globals.currentUser.username).then(function(response){
               $rootScope.globals.currentUser.nom = response.nom;
               $rootScope.globals.currentUser.prenom = response.prenom;
               $rootScope.globals.currentUser.username = response.email;
               $rootScope.globals.currentUser.password = response.password;
               vm.user = $rootScope.globals.currentUser;
               console.log(vm.user);
            });
        }
        
        function fillFields(){
            document.getElementById('nom').value = vm.user.nom;
            document.getElementById('prenom').value = vm.user.prenom;
            document.getElementById('password').value = vm.user.password;
            document.getElementById('password_confirmation').value = vm.user.password;
            if(vm.user.isPro){
                document.getElementById('siret').value = vm.user.siret;
                document.getElementById('company').value = vm.user.company;
            }
        }
        
        function updateProfile(){
            if(vm.company === undefined || vm.company === null)
                vm.company = "";
            UtilisateurService.updateProfile(vm.user.username, vm.password, vm.password_confirmation, vm.nom, vm.prenom, vm.company)
                .then(function (response) {
                    $rootScope.globals.currentUser.nom = response.nom;
                    $rootScope.globals.currentUser.prenom = response.prenom;
                    $rootScope.globals.currentUser.password = response.password;
                    vm.user = $rootScope.globals.currentUser;
                },
                function (errResponse) {
                    
                }
            );
        }
    }

})();
