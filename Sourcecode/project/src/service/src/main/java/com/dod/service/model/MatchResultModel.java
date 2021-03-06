package com.dod.service.model;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Models the information the client needs to display the end-game screen when the game ends.
 */
@XmlRootElement
public class MatchResultModel {

    private String winner;
    private int winnerCoins;
    private int score;

    public MatchResultModel(String winner, int winnerCoins, int score) {
        this.winner = winner;
        this.winnerCoins = winnerCoins;
        this.score = score;
    }

    public MatchResultModel() { }

    public String getWinner() {
        return winner;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }

    public int getWinnerCoins() {
        return winnerCoins;
    }

    public void setWinnerCoins(int winnerCoins) {
        this.winnerCoins = winnerCoins;
    }

    public int getScore() { return score; }

    public void setScore(int score) { this.score = score; }
}
