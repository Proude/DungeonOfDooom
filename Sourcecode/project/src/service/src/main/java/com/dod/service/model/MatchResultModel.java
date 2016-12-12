package com.dod.service.model;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Used to inform the client about the details of a game end
 */
@XmlRootElement
public class MatchResultModel {

    private String winner;
    private int winnerCoins;

    public MatchResultModel(String winner, int winnerCoins) {
        this.winner = winner;
        this.winnerCoins = winnerCoins;
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
}
