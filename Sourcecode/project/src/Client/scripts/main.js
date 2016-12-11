/**
 * 2016 Dungeon of Dooom University of Bath.
 */

game = [];
game.menu = [];
game.auth = [];
game.constants = [];
game.func = [];
game.match = [];
game.var = [];

game.var.xSize = 1400;
game.var.ySize = 900;
game.var.status = {};
game.var.scale = 50;
game.var.tiles = [];
game.var.characters = [];
game.var.renderer = {};
game.var.stage = {};
game.var.graphics = {};
game.var.playerTitles = [];
game.var.isRunning = false;
game.var.delta = 0;
game.var.timeStep = 1000 / 20;
game.var.lastFrameTimestamp = 0;


game.constants.api = "http://localhost:8080/";
game.constants.loginFailed = "Oops, that didn't work. Make sure your username/password are correct.";
game.constants.registrationFailed = "Oops, that didn't work. Fields cannot be empty or more than 255 characters.";

game.func.get = function(url, data, success, error) {
    $.ajax({
        type: "GET",
        url: url,
        data: data,
        success: success,
        error: error,
        xhrFields: {
            withCredentials: true
        }
    });
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

game.func.error = function( data, reason, exception ) {
    alert(' an error occurred :(');
    console.log(reason);
    console.log(exception);
};

game.auth.registerHook = function( data ) {
    game.menu.clearValidation();
    game.menu.login.css('display','none');
    game.menu.menu.css('display','block');
};

game.auth.loginHook = function( data ) {
    game.menu.clearValidation();
    game.menu.login.css('display','none');
    game.menu.menu.css('display','block');
};

game.menu.loginFormValidation = function(message ) {
    $('#login-validation').html(message);
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

    game.func.post(endpoint,
        { "username" : username, "password" : password },
        game.auth.registerHook,
        function() { game.menu.loginFormValidation(game.constants.registrationFailed)});
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

game.menu.openMatchLobby = function() {
    game.menu.menu.css('display','none');
    game.menu.lobby.css('display','block');
    game.match.list();
};

game.match.list = function() {
    //todo what if the webservice thinks you're already in a match?
    var endpoint = game.func.getApiPath("match","list");
    game.func.get(endpoint, {}, game.menu.displayMatchList, game.menu.error);
};

game.menu.displayMatchList = function( data ) {
    var matchList = $('#match-list');
    matchList.empty();

    $.each( data, function( i, match ) {
        var entry = $( String.format("<p><a data-id='{2}' class='join-link'>Join</a> {0}'s game with {1} players</p>", match.playerNames[0], match.playerNames.length, match.id) );
        matchList.append(entry);
    });

    $(".join-link").click(game.match.join);
};

game.match.join = function( data ) {
    var id = $(data.currentTarget).data("id");

    var endpoint = game.func.getApiPath("match","join");
    game.func.post(endpoint, { "matchId" : id }, game.menu.displayMatchMenu, game.menu.error);
};

game.match.new = function() {
    var endpoint = game.func.getApiPath("match","new");
    //todo add level choosing
    game.func.post(endpoint, { "level" : "1" }, game.menu.displayMatchMenu);
};

game.match.start = function() {
    var endpoint = game.func.getApiPath("match","start");
    game.func.post(endpoint, null, game.menu.initGameScreen, game.func.error);
};

game.menu.initGameScreen = function() {
    game.menu.game.empty();

    game.var.renderer = PIXI.autoDetectRenderer(game.var.xSize, game.var.ySize);
    game.var.renderer.backgroundColor = 0x8c8c8c;
    game.menu.game.append(game.var.renderer.view);

    game.var.stage = new PIXI.Container();
    game.var.graphics = new PIXI.Graphics();
    game.var.stage.addChild(game.var.graphics);

    game.menu.match.css('display', 'none');
    game.menu.game.css('display', 'block');

    game.var.isRunning = true;
    requestAnimationFrame(game.updateGame);
};

game.initPlayerTitles = function( characters ) {
    var style = {
        fontFamily : 'Arial',
        fontSize : '18px',
        fontStyle : 'italic',
        fontWeight : 'bold',
        fill : '#F7EDCA',
        stroke : '#4a1850',
        strokeThickness : 5,
        dropShadow : true,
        dropShadowColor : '#000000',
        dropShadowAngle : Math.PI / 6,
        dropShadowDistance : 4
    };

    $.each( characters, function(i, character) {
        game.var.playerTitles[character.playerName] = new PIXI.Text(character.playerName, style);
        game.var.stage.addChild(game.var.playerTitles[character.playerName]);
    });
};

game.render = function() {
    game.var.graphics.clear();

    for(x = 0; x < game.var.tiles.length; x++) {
        var row = game.var.tiles[x];
        if(typeof row !== 'undefined') {
            for (y = 0; y < game.var.tiles[x].length; y++) {
                if (game.var.tiles[x][y].type == 0) {
                    game.var.graphics.beginFill(0x000000);
                    game.var.graphics.drawRect(x * game.var.scale, y * game.var.scale, game.var.scale, game.var.scale);
                    game.var.graphics.endFill();
                }
                else if (game.var.tiles[x][y].type == 1) {
                    game.var.graphics.beginFill(0xbf8040);
                    game.var.graphics.drawRect(x * game.var.scale, y * game.var.scale, game.var.scale, game.var.scale);
                    game.var.graphics.endFill();
                }
                else if (game.var.tiles[x][y].type == 2) {
                    game.var.graphics.beginFill(0xffff66);
                    game.var.graphics.drawRect(x * game.var.scale, y * game.var.scale, game.var.scale, game.var.scale);
                    game.var.graphics.endFill();
                }

                if(game.var.tiles[x][y].character !== null) {
                    var posx = x * game.var.scale - game.var.scale / 2;
                    var posy = y * game.var.scale - game.var.scale / 2;

                    game.var.graphics.beginFill(0xff2222);
                    game.var.graphics.drawCircle(posx, posy, game.var.scale / 2);
                    game.var.graphics.endFill();

                    game.var.playerTitles[game.var.tiles[x][y].character.playerName].x = posx - game.var.scale / 2;
                    game.var.playerTitles[game.var.tiles[x][y].character.playerName].y = posy - game.var.scale;
                }
            }
        }
    }

    game.var.isRunning = true;
    game.var.renderer.render(game.var.stage);
};

game.updateStatus = function( status ) {
    game.var.characters = status.characters;

    $.each( status.tiles, function ( i, tile ) {
        tile.character = null;
        game.var.addTile(tile);
    });

    $.each( status.characters, function( i, character) {
        game.var.tiles[character.position.x][character.position.y].character = character;
    });

    if(game.var.playerTitles.length == 0) {
        game.initPlayerTitles(status.characters);
    }

    game.render();
};

game.var.addTile = function( tile ) {
    var pos = tile.position;

    if(typeof game.var.tiles[pos.x] === 'undefined') {
        game.var.tiles[pos.x] = [];
    }

    game.var.tiles[pos.x][pos.y] = tile;
};

game.fetchStatus = function() {
    var endpoint = game.func.getApiPath("game","status");
    game.var.status = game.func.get(endpoint, {}, game.updateStatus, game.func.error);
};

game.updateGame = function( timestamp ) {
    game.var.delta += timestamp - game.var.lastFrameTimestamp;
    game.var.lastFrameTimestamp = timestamp;

    if(game.var.delta > game.var.timeStep) {
        game.fetchStatus();
        game.var.delta -= game.var.timeStep;
    }

    if(game.var.isRunning) {
        requestAnimationFrame(game.updateGame);
    }
}

game.menu.displayMatchMenu = function( data ) {
    game.menu.lobby.css('display','none');
    game.menu.match.css('display','block');

    var matchDeatils = $("#match-details");
    matchDeatils.empty();

    matchDeatils.append($("<h2>Waiting to start.</h2>"));
    matchDeatils.append($("<h3>Players</h3>"));

    $.each( data.playerNames, function( i, name ) {
        var entry = $( String.format("<p>{0}</p>", name) );
        matchDeatils.append(entry);
    });
};

$( document ).ready(function() {
    game.menu.login = $('#login');
    game.menu.menu = $('#menu');
    game.menu.lobby = $('#lobby');
    game.menu.match = $('#match');
    game.menu.game = $('#game');

    game.menu.login.css('display', 'block');

    $('#register-btn').click(game.auth.register);
    $('#login-btn').click(game.auth.login);
    $('#menu-match-btn').click(game.menu.openMatchLobby);
    $('#new-match-btn').click(game.match.new);
    $('#start-match-btn').click(game.match.start);
});

