/**
 * 2016 Dungeon of Dooom University of Bath.
 */

game = [];
game.menu = [];
game.auth = [];
game.constants = [];
game.func = [];

game.constants.api = "http://localhost:8080/";

game.func.getApiPath = function(controller, action) {
    return game.constants.api + controller + "/" + action;
};

game.func.error = function( data, exception ) {
    alert(' an error occurred :(');
    console.log(exception);
}

game.auth.registerHook = function( data, moredata ) {
    alert('test hook');
};

game.auth.register = function() {
    var endpoint = game.func.getApiPath("player","register");
    var username = $("#username").val();
    var password = $("#password").val();
    var formData = new FormData();
    formData.append("username",username);
    formData.append("password",password);

    $.post(endpoint,
        { "username" : username, "password" : password },
        game.auth.registerHook)
        .fail(game.func.error)
};

$( document ).ready(function() {
    game.menu.login = $('#login');
    game.menu.menu = $('#menu');
    game.menu.match = $('#match');
    game.menu.game = $('#game');

    game.menu.login.css('display', 'block');

    $('#registerBtn').click(game.auth.register);
});

