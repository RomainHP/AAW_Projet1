(function () {
    'use strict';
 
    angular
        .module('app')
        .controller('RemoveAccountController', RemoveAccountController);
 
    RemoveAccountController.$inject = ['$location', 'FlashService', 'CompteService'];
    
    function RemoveAccountController($location, FlashService, CompteService) {
        var vm = this;
  
        vm.removeAccount = removeAccount;
        
        initController($location);

        function initController($location) {
            var accRemoved = $location.search().id;
            removeAccount(accRemoved);
        }
        
        function removeAccount(accRemoved) {
            if (accRemoved !== null) {
                console.log("removeAccount");
                CompteService.deleteAccount(accRemoved)
                    .then(function () {
                        FlashService.Success('Livret supprime avec succes', true);
                        $location.path('/consultation');
                    },
                    function (errResponse) {
                        FlashService.Error("Erreur : " + errResponse.data["errorMessage"], true);
                        $location.path('/consultation');
                    }
                );
            } else {
                FlashService.Error("Erreur", true);
                $location.path('/consultation');
            }
        }
    }
 
})();


