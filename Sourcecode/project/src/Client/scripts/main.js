/**
 * 2016 Dungeon of Dooom University of Bath.
 */

game = [];
game.menu = [];
game.auth = [];
game.constants = [];
game.func = [];

game.constants.api = "http://localhost:8080/";
game.constants.loginFailed = "Oops, that didn't work. Make sure your username/password are correct.";
game.constants.registrationFailed = "Oops, that didn't work. Fields cannot be empty or more than 255 characters."

game.func.getApiPath = function(controller, action) {
    return game.constants.api + controller + "/" + action;
};

game.func.error = function( data, exception ) {
    alert(' an error occurred :(');
    console.log(exception);
};

game.auth.registerHook = function( data ) {
    game.menu.clearValidation();
    alert('test hook');
};

game.auth.loginHook = function( data ) {
    game.menu.clearValidation();
    alert('test hook');
};

game.menu.loginFormValidation = function(message ) {
    $('#loginValidation').html(message);
};

game.menu.clearValidation = function() {
    var validatorelements = $('.validation');
    validatorelements.html('');
    validatorelements.css('display', 'none');
};

game.auth.register = function() {
    var endpoint = game.func.getApiPath("player","register");
    var username = $("#username").val();
    var password = $("#password").val();

    $.post(endpoint,
        { "username" : username, "password" : password })
        .done(game.auth.registerHook)
        .fail(new function() { game.menu.loginFormValidation(game.constants.registrationFailed)})
};

game.auth.login = function() {
    var endpoint = game.func.getApiPath("player","login");
    var username = $("#username").val();
    var password = $("#password").val();

    $.post(endpoint,
        { "username" : username, "password" : password })
        .done(game.auth.loginHook)
        .fail(new function() { game.menu.loginFormValidation(game.constants.loginFailed)})
};

$( document ).ready(function() {
    game.menu.login = $('#login');
    game.menu.menu = $('#menu');
    game.menu.lobby = $('#lobby');
    game.menu.match = $('#match');
    game.menu.game = $('#game');

    game.menu.login.css('display', 'block');

    $('#registerBtn').click(game.auth.register);
    $('#loginBtn').click(game.auth.login);
});

