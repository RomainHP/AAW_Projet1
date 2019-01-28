(function () {
    'use strict';
 
    angular
        .module('app')
        .controller('ConsultationController', ConsultationController);
 
    ConsultationController.$inject = ['CompteService', '$location', 'FlashService'];
    function ConsultationController(CompteService, $location, FlashService) {
        var vm = this;
  
        vm.consultation = consultation;
        vm.accounts;
        
        function consultation() {
            CompteService.consultation()
                    .then(function(response){
                vm.accounts = response.data;
                console.log("controlle "+response);
                console.log("controlle "+response.data);
            }
            );
        }
    }
 
})();

