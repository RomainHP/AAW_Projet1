(function () {
    'use strict';
 
    angular
        .module('app')
        .controller('ConsultationController', ConsultationController);
 
    ConsultationController.$inject = ['CompteService', '$location', 'FlashService'];
    function ConsultationController(CompteService, $location, FlashService) {
        var vm = this;
  
        vm.accounts = [];
        
        initConsultation();
        function initConsultation(){
            consultation();
        }
          
        function consultation() {
            CompteService.consultation()
                    .then(function(response){
                vm.accounts = JSON.parse(response);
            }
            );
        };
    };
 
})();

