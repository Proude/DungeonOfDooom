/**
 * Created by ancie on 11/12/2016.
 */

game = [];
game.menu = [];
game.auth = [];
game.constants = [];
game.func = [];

game.constants.api = "http://localhost:8080/";
game.constants.loginFailed = "Oops, that didn't work. Make sure your username/password are correct.";
game.constants.registrationFailed = "Oops, that didn't work. Fields cannot be empty or more than 255 characters.";

game.auth.loginHook = function( data ) {
    window.location.href = "http://localhost:63342/Dungeon of Doom/src/Client/menu/menu.html";
};

game.auth.registerHook = function( data ) {
    window.location.href = "http://localhost:63342/Dungeon of Doom/src/Client/login/loginPage.html";
};

game.menu.loginFormValidation = function(message ) {
    $('#login-validation').html(message);
};

game.menu.registerFormValidation = function(message ) {
    $('#register-validation').html(message);
};

game.func.post = function(url, data, success, error) {
    $.ajax({
        type: "POST",
        url: url,
        data: data,
        success: success,
        error: error,
        xhrFields: {
            withCredentials: true
        }
    });
};

game.func.getApiPath = function(controller, action) {
    return game.constants.api + controller + "/" + action;
};

game.auth.login = function() {
    var endpoint = game.func.getApiPath("player","login");
    var username = $("#username").val();
    var password = $("#password").val();

    game.func.post(endpoint,
        { "username" : username, "password" : password },
        game.auth.loginHook,
        //game.func.error);
        function() { game.menu.loginFormValidation(game.constants.loginFailed)});
};

game.auth.register = function() {
    var endpoint = game.func.getApiPath("player","register");
    var username = $("#uname").val();
    var password = $("#psw").val();

    game.func.post(endpoint,
        { "username" : username, "password" : password },
        game.auth.registerHook,
        function() { game.menu.registerFormValidation(game.constants.registrationFailed)});
};

$( document ).ready(function() {
    $('#register-btn').click(game.auth.register);
    $('#login-btn').click(game.auth.login);
});
