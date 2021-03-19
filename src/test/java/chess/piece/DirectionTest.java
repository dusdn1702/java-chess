package chess.piece;

import chess.domain.Point;
import chess.domain.piece.Direction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class DirectionTest {
    @DisplayName("Direction 확인")
    @Test
    void findDirection() {
        Direction direction = Direction.findDirection(Point.of("a1"), Point.of("h8"));
        Assertions.assertEquals(Direction.NORTH_EAST, direction);

        Direction direction2 = Direction.findDirection(Point.valueOf(1, 4), Point.valueOf(0,4));
        Assertions.assertEquals(Direction.NORTH, direction2);
    }
}
