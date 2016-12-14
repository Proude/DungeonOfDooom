/**
 * 2016 Dungeon of Dooom University of Bath.
 * "Part of the graphic tiles used in this program is the Public domain roguelike tileset "RLTiles".
 * Some of the tiles have been modified by our Team. You can find the original tileset at: http://rltiles.sf.net
 * You can find Dungeon Crawl Stone Soup modified tilesets at: http://code.google.com/p/crawl-tiles/downloads/list"
 * Tileset was downloaded from opengameart.org/content/dungeon-crawl-32x32-tiles
 */

game = [];
game.menu = [];
game.auth = [];
game.constants = [];
game.func = [];
game.match = [];
game.var = [];
game.match.var = [];
game.camera = {};

game.var.init = function() {
    game.var.xSize = 900;
    game.var.ySize = 600;
    game.var.playerCharacter = {};
    game.var.scale = 50;
    game.var.tiles = [];
    game.var.characters = [];
    game.var.minCoins = {};
    game.var.winText = [];
    game.var.renderer = {};
    game.var.stage = {};
    game.var.graphics = {};
    game.var.playerTitles = [];
    game.var.isRunning = false;
    game.var.delta = 0;
    game.var.timeStep = 1000 / 20;
    game.var.lastFrameTimestamp = 0;
    game.var.opacityVis = 1.0;
    game.var.opacityInvis = 0.3;
};
game.var.init();
game.var.colours = [];
game.var.colours.background = 0x000000;
game.var.colours.wall = 0x8c8c8c;
game.var.colours.floor = 0xbf8040;
game.var.colours.gold = 0xffff66;
game.var.colours.player = 0xff2222;
game.var.colours.exit = 0x2222ff;
game.var.colours.shaded = [];
game.var.colours.shaded.wall = 0x565656;
game.var.colours.shaded.floor = 0x8c5010;
game.var.colours.shaded.gold = 0xcccc33;
game.var.colours.shaded.player = 0xcc0000;
game.var.colours.shaded.exit = 0x0000cc;

game.match.var.isLobbying = false;
game.match.var.isWaitingTostart = false;
game.match.var.delta = 0;
game.match.var.timeStep = 1000 / 5;
game.match.var.lastFrameTimestamp = 0;

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

game.auth.hook = function( data ) {
    game.menu.clearValidation();
    $('#guest-header').css('display', 'none');
    $('#logged-in-header').css('display','block');
    game.menu.openMatchLobby();
};

game.menu.loginFormValidation = function(message ) {
    $('#login-validation').html(message);
};

game.menu.clearValidation = function() {
    var validatorElements = $('.validation');
    validatorElements.html('');
    validatorElements.css('display', 'none');
};

game.auth.register = function() {
    var endpoint = game.func.getApiPath("player","register");
    var username = $("#username").val();
    var password = $("#password").val();

    game.func.post(endpoint,
        { "username" : username, "password" : password },
        game.auth.hook,
        function() { game.menu.loginFormValidation(game.constants.registrationFailed)});
};

game.auth.login = function() {
    var endpoint = game.func.getApiPath("player","login");
    var username = $("#username").val();
    var password = $("#password").val();

    game.func.post(endpoint,
        { "username" : username, "password" : password },
        game.auth.hook,
        function() { game.menu.loginFormValidation(game.constants.loginFailed)});
};

game.menu.openMatchLobby = function() {
    game.menu.allSections.css('display','none');
    game.menu.lobby.css('display','block');
    game.match.var.isLobbying = true;
    requestAnimationFrame(game.match.updateMatchList);
};

game.menu.openTutorial = function() {
    game.match.var.isLobbying = false;
    game.menu.allSections.css('display','none');
    game.menu.tutorial.css('display','block');
};

game.menu.openScoreboard = function() {
    game.match.var.isLobbying = false;
    var endpoint = game.func.getApiPath("score","top");
    game.func.get(endpoint, { }, game.menu.displayScoreboard, game.func.error);
};

game.menu.displayScoreboard = function( scoreBoard ) {
    $('#score-table tbody td').remove();
    $.each(scoreBoard.scores, function(i, score) {
        if(score != null) {
            $('#score-table tbody').append($(String.format("<tr><td>{0}</td><td>{1}</td></tr>", score.username, score.value)))
        }
    });

    game.menu.allSections.css('display','none');
    game.menu.scoreboard.css('display','block');
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
    game.match.var.isLobbying = false;
    game.match.var.isWaitingTostart = true;

    var endpoint = game.func.getApiPath("match","join");
    game.func.post(endpoint, { "matchId" : id }, game.menu.displayMatchMenu, game.menu.error);
    requestAnimationFrame(game.match.updateStatus);
};

game.match.new = function() {
    var endpoint = game.func.getApiPath("match","new");
    game.match.var.isLobbying = false;
    game.match.var.isWaitingTostart = true;

    var level = game.menu.levelChooser.val();
    game.func.post(endpoint, { "level" : level }, game.menu.displayMatchMenu);
    requestAnimationFrame(game.match.updateStatus);
};

game.match.start = function() {
    game.match.var.isWaitingTostart = false;
    var endpoint = game.func.getApiPath("match","start");
    requestAnimationFrame(function() {game.func.post(endpoint, null, game.menu.initGameScreen, game.func.error) });
};

game.menu.initGameScreen = function() {
    game.var.init();
    game.menu.gameContainer.empty();

    game.var.renderer = PIXI.autoDetectRenderer(game.var.xSize, game.var.ySize);
    game.var.renderer.backgroundColor = game.var.colours.background;
    game.var.renderer.transparent = true;
    game.menu.gameContainer.append(game.var.renderer.view);

    game.var.stage = new PIXI.Container();
    // game.var.graphics = new PIXI.Graphics();
    // game.var.stage.addChild(game.var.graphics);

    game.menu.match.css('display', 'none');
    game.menu.game.css('display', 'block');

    game.var.isRunning = true;
    requestAnimationFrame(game.updateGame);
};

game.initTextWinCondition = function( character ) {
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

    game.var.winText[character.playerName] = new PIXI.Text('Collect '+ game.var.minCoins +' coins minimum to win! You collected ' + game.var.playerCharacter.noCoins + ' coins!', style);
}

game.initPlayerTitle = function( character ) {
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

    game.var.playerTitles[character.playerName] = new PIXI.Text(character.playerName, style);
};

game.render = function() {
    //game.var.graphics.clear();
    game.var.stage = new PIXI.Container();

    for(x = 0; x < game.var.tiles.length; x++) {
        var row = game.var.tiles[x];
        if(typeof row !== 'undefined') {
            for (y = 0; y < game.var.tiles[x].length; y++) {
                var tile = game.var.tiles[x][y];

                if(typeof tile !== 'undefined') {
                    var tilePositionX = (x * game.var.scale) - game.camera.x;
                    var tilePositionY = (y * game.var.scale) - game.camera.y;

                    if (tile.type == 0) {
                        var wall = PIXI.Sprite.fromImage('assets/wall.png');
                        wall.x = tilePositionX;
                        wall.y = tilePositionY;
                        wall.alpha = tile.visible ? game.var.opacityVis : game.var.opacityInvis;
                        game.var.stage.addChild(wall);
                    }
                    else if (tile.type == 1) {
                        var floor = PIXI.Sprite.fromImage('assets/floor.png');
                        floor.x = tilePositionX;
                        floor.y = tilePositionY;
                        floor.alpha = tile.visible ? game.var.opacityVis : game.var.opacityInvis;
                        game.var.stage.addChild(floor);
                    }
                    else if (tile.type == 2) {
                        var coin = PIXI.Sprite.fromImage('assets/coin.png');
                        coin.x = tilePositionX;
                        coin.y = tilePositionY;
                        coin.alpha = tile.visible ? game.var.opacityVis : game.var.opacityInvis;
                        game.var.stage.addChild(coin);
                    }
                    else if(tile.type == 3) {
                        var exit = PIXI.Sprite.fromImage('assets/exit.png');
                        exit.x = tilePositionX;
                        exit.y = tilePositionY;
                        exit.alpha = tile.visible ? game.var.opacityVis : game.var.opacityInvis;
                        game.var.stage.addChild(exit);
                    }

                    if (tile.visible && tile.character !== null) {
                        var positionX = tilePositionX + game.var.scale / 2;
                        var positionY = tilePositionY + game.var.scale / 2;

                        var char = PIXI.Sprite.fromImage('assets/char.png');
                        char.x = positionX - game.var.scale / 2;
                        char.y = positionY - game.var.scale / 2;
                        game.var.stage.addChild(char);

                        var character = game.var.tiles[x][y].character;
                        var playerTitle = game.var.playerTitles[character.playerName];
                        if(typeof playerTitle === 'undefined') {
                            game.initPlayerTitle(character);
                        }
                        else {
                            playerTitle.x = positionX - game.var.scale;
                            playerTitle.y = positionY - game.var.scale;
                        }
                        game.var.stage.addChild(game.var.playerTitles[character.playerName]);
                        game.initTextWinCondition(game.var.tiles[x][y].character);
                        game.var.stage.addChild(game.var.winText[character.playerName]);
                    }
                }
            }
        }
    }

    game.var.isRunning = true;
    game.var.renderer.render(game.var.stage);
};

game.setAllTilesNotVisible = function() {
    $.each(game.var.tiles, function(x, row) {
        if(typeof row != 'undefined') {
            $.each(row, function(y, tile) {
                if(typeof tile != 'undefined')
                tile.visible = false;
            });
        }
    });
};


game.updateStatus = function( status ) {
    game.var.characters = status.characters;
    game.var.playerCharacter = status.playerCharacter;
    game.var.minCoins = status.minNumOfCoins;

    game.camera.x = (game.var.playerCharacter.position.x * game.var.scale) - (game.var.xSize / 2);
    game.camera.y = (game.var.playerCharacter.position.y * game.var.scale) - (game.var.ySize / 2);

    game.setAllTilesNotVisible();
    $.each( status.tiles, function ( i, tile ) {
        tile.character = null;
        game.var.addTile(tile);
    });

    $.each( status.characters, function( i, character) {
        game.var.tiles[character.position.x][character.position.y].character = character;
    });

    if(status.hasEnded) {
        game.var.isRunning = false;
        game.end();
    }
    else {
        game.render();
    }
};

game.var.addTile = function( tile ) {
    var pos = tile.position;

    if(typeof game.var.tiles[pos.x] === 'undefined') {
        game.var.tiles[pos.x] = [];
    }

    tile.visible = true;
    game.var.tiles[pos.x][pos.y] = tile;
};

game.fetchStatus = function() {
    var endpoint = game.func.getApiPath("game","status");
    game.func.get(endpoint, {}, game.updateStatus, game.func.error);
};

game.updateGame = function( timestamp ) {
    if(game.var.lastFrameTimestamp == 0) {
        game.var.lastFrameTimestamp = timestamp + game.var.timeStep;
    }
    game.var.delta += timestamp - game.var.lastFrameTimestamp;
    game.var.lastFrameTimestamp = timestamp;

    if(game.var.delta > game.var.timeStep) {
        game.fetchStatus();
        game.var.delta -= game.var.timeStep;
    }

    if(game.var.isRunning) {
        requestAnimationFrame(game.updateGame);
    }
};

game.match.updateMatchList = function( timestamp ) {
    if(game.match.var.lastFrameTimestamp == 0) {
        game.match.var.lastFrameTimestamp = timestamp + game.match.var.timeStep;
    }
    game.match.var.delta += timestamp - game.match.var.lastFrameTimestamp;
    game.match.var.lastFrameTimestamp = timestamp;

    if(game.match.var.delta >= game.match.var.timeStep) {
        game.match.list();
        game.match.var.delta -= game.match.var.timeStep;
    }

    if(game.match.var.isLobbying) {
        requestAnimationFrame(game.match.updateMatchList);
    }
};

game.match.updateStatus = function( timestamp ) {
    if(game.match.var.lastFrameTimestamp == 0) {
        game.match.var.lastFrameTimestamp = timestamp + game.match.var.timeStep;
    }
    game.match.var.delta += timestamp - game.match.var.lastFrameTimestamp;
    game.match.var.lastFrameTimestamp = timestamp;

    if(game.match.var.delta >= game.match.var.timeStep) {
        game.match.fetchStatus();
        game.match.var.delta -= game.match.var.timeStep;
    }

    if(game.match.var.isWaitingTostart) {
        requestAnimationFrame(game.match.updateStatus);
    }
};

game.match.fetchStatus = function() {
    var endpoint = game.func.getApiPath("match","status");
    game.func.get(endpoint, {}, game.menu.displayMatchMenu, game.func.error);
};

game.menu.displayMatchMenu = function( data ) {
    game.menu.lobby.css('display','none');
    game.menu.match.css('display','block');

    var matchDeatils = $("#match-details");
    matchDeatils.empty();

    matchDeatils.append($("<h2>Waiting to start.</h2>"));
    matchDeatils.append($(String.format("<p>To add a bot use the following ID: {0}</p>", data.id)));
    matchDeatils.append($("<h3>Players:</h3>"));

    $.each( data.playerNames, function( i, name ) {
        var entry = $( String.format("<p>{0}</p>", name) );
        matchDeatils.append(entry);
    });

    if(data.state == 'Ingame') {
        game.match.var.isWaitingTostart = false;
        game.menu.initGameScreen();
    }
};

game.match.leave = function() {
    game.var.isRunning = false;

    var endpoint =  game.func.getApiPath("match","leave");
    requestAnimationFrame(function() {game.func.post(endpoint, { }, game.menu.openMatchLobby, game.func.error)});
};

game.menu.move = function( key ) {
    var endpoint = game.func.getApiPath("game","move");
    game.var.status = game.func.post(endpoint, {"key" : key}, game.updateStatus, game.func.error);
};

game.menu.showEndGameScreen = function( result ) {
    if(result.winner == game.var.playerCharacter.playerName) {
        $('#end-game-title').html("YOU WIN!")
    }
    else {
        $('#end-game-title').html("YOU LOOSE!")
    }
    $('#end-game-detail').html(String.format("{0} wins with {1} coins", result.winner, result.winnerCoins));
    game.menu.gameContainer.empty();
    game.menu.game.css('display','none');
    game.menu.end.css('display','block');
};

game.end = function() {
    game.var.isRunning = false;
    var endpoint = game.func.getApiPath("match","result");
    game.func.get(endpoint, { }, game.menu.showEndGameScreen, game.func.error);
};

$( document ).ready(function() {
    game.menu.login = $('#login');
    game.menu.lobby = $('#lobby');
    game.menu.levelChooser = $('#level');
    game.menu.match = $('#match');
    game.menu.tutorial = $('#tutorial');
    game.menu.end = $('#end-game');
    game.menu.scoreboard = $('#score');
    game.menu.game = $('#game');
    game.menu.gameContainer = $('#game-container');
    game.menu.allSections = $('section');

    game.menu.login.css('display', 'block');

    $('#register-btn').click(game.auth.register);
    $('#login-btn').click(game.auth.login);
    $('#new-match-btn').click(game.match.new);
    $('#start-match-btn').click(game.match.start);
    $('#match-leave-btn').click(game.match.leave);
    $('#return-btn').click(game.match.leave);
    $('#lobby-link').click(game.menu.openMatchLobby);
    $('#tutorial-link').click(game.menu.openTutorial);
    $('#score-link').click(game.menu.openScoreboard);

    window.addEventListener('keydown', function(event) {
        if (game.var.isRunning) {
            switch (event.keyCode) {
                case 65:
                case 37: // Left
                    game.menu.move('A');
                    break;
                case 87:
                case 38: // Up
                    game.menu.move('W');
                    break;
                case 68:
                case 39: // Right
                    game.menu.move('D');
                    break;
                case 83:
                case 40: // Down
                    game.menu.move('S');
                    break;
            }
        }
    }, false);
});