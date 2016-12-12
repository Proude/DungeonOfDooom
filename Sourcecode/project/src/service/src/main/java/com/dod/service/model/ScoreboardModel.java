package com.dod.service.model;

import com.dod.models.Score;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Stores a number of scores for JSON representation
 */
@XmlRootElement
public class ScoreboardModel {
    Score[] scores;

    public ScoreboardModel(Score[] scores) {
        this.scores = scores;
    }

    public ScoreboardModel() {

    }

    public Score[] getScores() {
        return scores;
    }

    public void setScores(Score[] scores) {
        this.scores = scores;
    }
}
