(function () {
    'use strict';
 
    angular
        .module('app')
        .controller('RemoveAccountAdminController', RemoveAccountAdminController);
 
    RemoveAccountAdminController.$inject = ['$location', 'FlashService', 'AdminService'];
    
    function RemoveAccountAdminController($location, FlashService, AdminService) {
        var vm = this;
  
        vm.removeAccountAdmin = removeAccountAdmin;
        
        initController($location);

        function initController($location) {
            var accRemoved = $location.search().id;
            removeAccountAdmin(accRemoved);
        }
        
        function removeAccountAdmin(accRemoved) {
            if (accRemoved !== null) {
                AdminService.deleteAccountAdmin(accRemoved)
                    .then(function () {
                        FlashService.Success('Livret supprime avec succes', true);
                        $location.path('/consultation_admin');
                    },
                    function (errResponse) {
                        FlashService.Error("Erreur : " + errResponse.data["errorMessage"], true);
                        console.log(errResponse);
                        $location.path('/consultation_admin');
                    }
                );
            } else {
                FlashService.Error("Erreur", true);
                $location.path('/consultation_admin');
            }
        }
    }
 
})();


