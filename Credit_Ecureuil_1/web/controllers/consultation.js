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
        vm.selectOwnAcc = selectOwnAcc;
        vm.selectOtherAcc = selectOtherAcc;
        vm.virement = virement;
        vm.createLinkedAccount = createLinkedAccount;
        vm.detailsCompte = detailsCompte;
        
        var mail = $rootScope.globals.currentUser.username;
        var srcSelected = null;
        var destSelected = null;
        var alreadyPrinted = false;
//        var idCpt = vm.account.id;
        
        initConsultation($rootScope);
        function initConsultation($rootScope){
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
            if (vm.accounts !== null && alreadyPrinted === false){
                var index = vm.accounts.Compte.length;
                for(var i = 0; i<index; i++){
                    var tmp = i +1;
                    document.getElementById('table').innerHTML += '<td>' + tmp+ '</td>'
                    + '<td scope="row">' + vm.accounts.Compte[i].id[0] + '</td>'
                    + '<td scope="row">' + vm.accounts.Compte[i].prop[0] + '</td>'
                    + '<td scope="row">' + vm.accounts.Compte[i].name[0] + '</td>'
                    + '<td scope="row">' + vm.accounts.Compte[i].solde[0] + '</td>'
                    + '<td scope="row">'
                    + '<form class="form" ng-submit="vm.detailsCompte(login)">'
                    +   '<div class="form-group mb-3">'
                    +       '<input type="hidden" ng-value="'+vm.accounts.Compte[i].id[0]+'" ng-model="vm.account.id" role="form">'
                    +       '<button type="submit" class="btn btn-primary btn-md">Détails</button>'
                    +   '</div>'
                    + '</form>'
                    + '</td>'
                    ;
                }
                alreadyPrinted = true;
            }
        }
        
        function virement(){            
            var montant = document.getElementById("value").value;
            console.log("src : " + srcSelected + " dest : " + destSelected + " montant : " + montant);
            CompteService.virement(srcSelected, destSelected, montant).then(function () {
                    FlashService.Success('Virement réalisé avec succès', true);
                },
                function (errResponse) {
                    FlashService.Error("Erreur : " + errResponse.data["errorMessage"], true);
                }
            );
        }
        
        function createLinkedAccount(){
            var i = 2;
            var listeCoProprietaire = [];
            listeCoProprietaire.push(document.getElementById("field1").value);
            var tmp = document.getElementById("field"+i).value;
            while(tmp !== undefined && tmp !== null){
                console.log(tmp);
                listeCoProprietaire.push(tmp);
                i++;
                if(document.getElementById("field"+i) !== null && document.getElementById("field"+i) !== undefined)
                    tmp = document.getElementById("field"+i).value;
                else
                    tmp = null;
            }
            CompteService.createLinkedAccount(vm.account.name, mail, listeCoProprietaire).then(function () {
                    FlashService.Success('Nouveau compte joint crée avec succès', true);
                },
                function (errResponse) {
                    FlashService.Error("Erreur : " + errResponse.data["errorMessage"], true);
                }
            );
        }
        
        function detailsCompte(login){
            console.log(vm.acocunt.id);
            CompteService.details(login, vm.acocunt.id).then(function () {
                    $location.path("#!/details");
                },
                function (errResponse) {
                    FlashService.Error("Erreur : " + errResponse.data["errorMessage"], true);
                }
            );
        }
        
                
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
        
        function displayOwnAcc(){
            if(vm.accounts !== null){
                var index = vm.accounts.Compte.length;
                document.getElementById("id").innerHTML =
                    '<select class="custom-select mb-2 mr-sm-2 mb-sm-0" id="id" ng-change="' + selectOwnAcc()+'">' +    
                    '<option value="default" selected>Selectionner un compte</option>'+
                    '</select>';
                for(var i = 0; i<index; i++){
                    document.getElementById('id').innerHTML += 
                    '<option value="'+ vm.accounts.Compte[i].id[0]+'">' + vm.accounts.Compte[i].name[0] + '</option>';
                }
            }
        }
        
        function displayOtherAcc(){
            if(vm.accounts !== null && vm.allAccounts !== null){
                document.getElementById("id_dest").innerHTML =
                    '<select class="custom-select mb-2 mr-sm-2 mb-sm-0" id="id" ng-change="' + selectOtherAcc()+'">' +    
                    '<option value="default" selected>Selectionner un compte</option>'+
                    '</select>';
                
                var res;
                for(var i=0; i<vm.allAccounts.name.length;i++){
                    res +=
                    '<option value="'+ vm.allAccounts.id[i]+'">' + vm.allAccounts.name[i] 
                    + ' (' + vm.allAccounts.prop[i] + ')</option>';
                }
                
                document.getElementById('id_dest').innerHTML += res;
            }
        }
    };
 
})();

