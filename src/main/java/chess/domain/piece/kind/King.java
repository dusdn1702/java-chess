package chess.domain.piece.kind;

import java.util.Optional;

import chess.domain.board.Point;
import chess.domain.piece.Color;
import chess.domain.piece.Direction;

public class King extends Piece {
    private static final int KING_SCORE = 0;
    private static final String KING_NAME = "k";

    public King(Color color, Point point) {
        super(KING_NAME, color, point);
    }

    @Override
    public Optional<Direction> direction(Piece target) {
        Direction direction = Direction.findDirection(this.point, target.point);
        int distance = this.point.calculateDistance(target.point);

        if (distance == MOVE_STRAIGHT_ONE_SQUARE || distance == MOVE_DIAGONAL_ONE_SQUARE) {
            return Optional.of(direction);
        }
        throw new IllegalArgumentException(IMPOSSIBLE_ROUTE_ERROR_MESSAGE);
    }

    @Override
    public Point moveOneStep(Point target, Direction direction) {
        return target;
    }

    @Override
    public double score() {
        return KING_SCORE;
    }

    @Override
    public boolean isEmptyPiece() {
        return false;
    }

    @Override
    public boolean isKing() {
        return true;
    }

    @Override
    public boolean isPawn() {
        return false;
    }
}
