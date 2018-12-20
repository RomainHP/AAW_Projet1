(function () {
    'use strict';
 
    angular
        .module('app')
        .factory('AuthentificationService', AuthentificationService);
 
    AuthentificationService.$inject = ['$cookies', '$rootScope', 'UserService'];
    function AuthentificationService($cookies, $rootScope, UserService) {
        var service = {};
 
        service.Login = Login;
        service.SetCredentials = SetCredentials;
        service.ClearCredentials = ClearCredentials;
 
        return service;
 
        function Login(username, password, callback) {
            var response;
            UserService.GetByName(username)
                .then(function (user) {
                    if (user !== null) {
                        if (user.password === password) {
                            response = { success: true };
                        }
                        else {
                            response = { success: false, message: 'Erreur login / Mot de passe' };
                        }
                    }
                    else {
                        response = { success: false, message: 'Compte inexistant' };
                    }
                    callback(response);
                });
            }
 
        function SetCredentials(username, password) {
            $rootScope.globals = {
                currentUser: {
                    username: username,
                    password: password
                }
            };
            $cookies.putObject('globals', $rootScope.globals);
        }
 
        function ClearCredentials() {
            $cookies.remove('globals');
        }
    }
 
})();