package com.dod.models;

/**
 * The state of a match, allowing us to know whether it's started or lobbying.
 */
public enum MatchState {
    Lobbying,
    Ingame,
    Over
}
