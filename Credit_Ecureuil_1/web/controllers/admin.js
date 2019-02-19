(function () {
    'use strict';

    angular
        .module('app')
        .controller('AdminController', AdminController);

    AdminController.$inject = ['$rootScope' ,'AdminService', 'FlashService'];
    function AdminController($rootScope, AdminService, FlashService) {
        
        var vm = this;
        
        vm.user = null;
        vm.allAccounts = null;
        var srcSelected = null;
        var destSelected = null;
        
        vm.consultation_admin = consultation_admin;
        vm.displayAccount = displayAccount;
        vm.virement = virement;
        vm.ajout_livret_admin = ajout_livret_admin;
        
        vm.selectOwnAcc = selectOwnAcc;
        vm.selectOtherAcc = selectOtherAcc;
        vm.displayAcc1 = displayAcc1;
        vm.displayAcc2 = displayAcc2;
        
        initController($rootScope);

        function initController($rootScope) {
           vm.user = $rootScope.globals.currentUser;
           consultation_admin();
        }
        
        function consultation_admin(){
            AdminService.consultation().then(function(response){
                vm.allAccounts = response;
            });
        }
        
        function displayAccount(){
            if(vm.allAccounts !== null){
                var index = vm.allAccounts.proprio.length;
                for(var i = 0; i<index; i++){
                    if(vm.allAccounts.supprimable[i] === "true"){
                        var butDel = '<button class="btn btn-primary btn-md" onClick="window.location.href=\'#!/delete_account_admin?id='+vm.allAccounts.id[i]+'\'">Supprimer</button>';
                    }else{
                        var butDel = '';
                    }
                    var tmp = i +1;
                    document.getElementById('table').innerHTML += '<td>' + tmp+ '</td>'
                    + '<td scope="row">' + vm.allAccounts.id[i] + '</td>'
                    + '<td scope="row">' + vm.allAccounts.proprio[i] + '</td>'
                    + '<td scope="row">' + vm.allAccounts.nomCompteSrc[i] + '</td>'
                    + '<td scope="row">' + vm.allAccounts.solde[i] + '</td>'
                    + '<td scope="row">'
                    + '<button class="btn btn-primary btn-md" onClick="window.location.href=\'#!/details?id='+vm.allAccounts.id[i]+'\'">Details</button>'
                    + butDel
                    + '</td>'
                    ;
                }
            }
        }
        
        function virement(){            
            var montant = document.getElementById("value").value;
            AdminService.virement(srcSelected, destSelected, montant).then(function () {
                    FlashService.Success('Virement réalisé avec succès', true);
                },
                function (errResponse) {
                    FlashService.Error("Erreur : " + errResponse.data["errorMessage"], true);
                }
            );
        }
        
        function ajout_livret_admin(){
            AdminService.ajout_livret(vm.account.name, vm.account.mail).then(function (){
                FlashService.Success('Compte créé avec succès', true);
            },
            function(errResponse){
                FlashService.Error("Erreur : " + errResponse.data["errorMessage"], true);
            });
        }
        
        //====================FONCTIONS D'AFFICHAGE==============================//
        function selectOwnAcc(){
            var tmp = document.getElementById("id");
            if(tmp.options[tmp.selectedIndex] !== undefined)
                srcSelected = tmp.options[tmp.selectedIndex].value;
        }
        
        function selectOtherAcc(){
            var tmp = document.getElementById("id_dest");
            if(tmp.options[tmp.selectedIndex] !== undefined)
                destSelected = tmp.options[tmp.selectedIndex].value;
        }
        
        function displayAcc1(){
            if(vm.allAccounts !== null && vm.allAccounts !== undefined){
                var index = vm.allAccounts.id.length;
                document.getElementById("id").innerHTML =
                    '<select class="custom-select mb-2 mr-sm-2 mb-sm-0" id="id" ng-change="' + selectOwnAcc()+'">' +    
                    '<option value="default" selected>Selectionner un compte</option>'+
                    '</select>';
                for(var i = 0; i<index; i++){
                    document.getElementById("id").innerHTML += 
                    '<option value="'+ vm.allAccounts.id[i]+'">' + vm.allAccounts.nomCompteSrc[i] + '</option>';
                }
            }
        }
        
        function displayAcc2(){
            if(vm.allAccounts !== null && vm.allAccounts !== undefined){
                var index = vm.allAccounts.id.length;
                document.getElementById("id_dest").innerHTML =
                    '<select class="custom-select mb-2 mr-sm-2 mb-sm-0" id="id" ng-change="' + selectOtherAcc()+'">' +    
                    '<option value="default" selected>Selectionner un compte</option>'+
                    '</select>';
                for(var i = 0; i<index; i++){
                    document.getElementById("id_dest").innerHTML += 
                    '<option value="'+ vm.allAccounts.id[i]+'">' + vm.allAccounts.nomCompteSrc[i] + '</option>';
                }
            }
        }
    }

})();
