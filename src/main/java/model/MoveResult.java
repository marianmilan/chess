package model;

public enum MoveResult {
    VALID,
    INVALID,
    PROMOTION,
    CASTLE_KINGSIDE,
    CASTLE_QUEENSIDE,
    CHECK_MATE,
    STALE_MATE
}
