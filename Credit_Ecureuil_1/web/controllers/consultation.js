(function () {
    'use strict';
 
    angular
        .module('app')
        .controller('ConsultationController', ConsultationController);
 
    ConsultationController.$inject = ['$rootScope', 'CompteService', '$location', 'FlashService'];
    function ConsultationController($rootScope, CompteService, $location, FlashService) {
        var vm = this;
  
        vm.user = null;
        vm.accounts = null;
        vm.allAccounts = null;
        
        
        vm.createAccount = createAccount;
        vm.displayAccount = displayAccount;
        vm.displayOwnAcc = displayOwnAcc;
        vm.displayOtherAcc = displayOtherAcc;
        vm.virement = virement;
        
        var mail = $rootScope.globals.currentUser.username;
        var srcSelected = null;
        var destSelected = null;
        
        initConsultation();
        function initConsultation(){
            vm.user = $rootScope.globals.currentUser;
            consultation(mail);
            CompteService.getAllAccounts().then(function(response){
                vm.allAccounts = response;
            });
        }
          
        function consultation(mail) {
            CompteService.consultation(mail)
                    .then(function(response){
                        vm.accounts = response;
                    
                    }
            );
        };
        
        function createAccount(){
            CompteService.createAccount(vm.account.name, mail).then(function () {
                    FlashService.Success('Nouveau livret crée avec succès', true);
                },
                function (errResponse) {
                    FlashService.Error("Erreur : " + errResponse.data["errorMessage"], true);
                }
            );
        }
        
        function displayAccount(){
            if (vm.accounts !== null){
                var index = vm.accounts.Compte.length;
                for(var i = 0; i<index; i++){
                    document.getElementById('table').innerHTML += '<td>' + i+1+ '</td>'
                    + '<td scope="row">' + vm.accounts.Compte[i].id[0] + '</td>'
                    + '<td scope="row">' + vm.accounts.Compte[i].prop[0] + '</td>'
                    + '<td scope="row">' + vm.accounts.Compte[i].name[0] + '</td>'
                    + '<td scope="row">' + vm.accounts.Compte[i].solde[0] + '</td>'
                    + '<td scope="row">'
                    + '<form class="form" action="details_compte.htm" method="post">'
                    +   '<div class="form-group mb-3">'
                    +       '<input type="hidden" class="form-control" name="idCpt" value=account.getId()>'
                    +       '<button type="submit" class="btn btn-primary btn-md">Détails</button>'
                    +   '</div>'
                    + '</form>'
                    + '</td>'
                    ;
                }
            }
        }
        
        function displayOwnAcc(){
            if(vm.accounts !== null){
                var index = vm.accounts.Compte.length;
                document.getElementById("id").innerHTML =
                    '<option value="default" selected>Selectionner un compte</option>';
                for(var i = 0; i<index; i++){
                    document.getElementById('id').innerHTML += 
                    '<option value="'+ vm.accounts.Compte[i].id[0]+'">' + vm.accounts.Compte[i].name[0] + '</option>';
                }
            }
        }
        
        function displayOtherAcc(){
            if(vm.accounts !== null && vm.allAccounts !== null){
                document.getElementById("id_dest").innerHTML =
                    '<option value="default" selected>Selectionner un compte</option>';
                
                var res;
                for(var i=0; i<vm.allAccounts.name.length;i++){
                    res +=
                    '<option value="'+ vm.allAccounts.id[i]+'">' + vm.allAccounts.name[i] 
                    + ' (' + vm.allAccounts.prop[i] + ')</option>';
                }
                
                document.getElementById('id_dest').innerHTML += res;
            }
        }
        
        function virement(){
            var tmp = document.getElementById("id");
            srcSelected = tmp.options[tmp.selectedIndex].value;
            
            tmp = document.getElementById("id_dest");
            destSelected = tmp.options[tmp.selectedIndex].value;
            
            var montant = document.getElementById("value").value;
            
            console.log(srcSelected + " " + destSelected + " " + montant);
            
            CompteService.virement(srcSelected, destSelected, montant).then(function () {
                    FlashService.Success('Virement réalisé avec succès', true);
                },
                function (errResponse) {
                    FlashService.Error("Erreur : " + errResponse.data["errorMessage"], true);
                }
            );
        }
    };
 
})();

