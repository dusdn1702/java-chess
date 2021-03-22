package chess.piece;

import static chess.domain.piece.Color.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import chess.domain.board.Point;
import chess.domain.piece.Direction;
import chess.domain.piece.PieceType;
import chess.domain.piece.kind.Empty;
import chess.domain.piece.kind.Rook;

public class RookTest {
	@DisplayName("Rook 생성")
	@Test
	public void create() {
		Rook rook1 = new Rook(BLACK, Point.valueOf(0, 0));
		assertThat(PieceType.findPiece(0, 0)).isEqualTo(rook1);
		Rook rook2 = new Rook(BLACK, Point.valueOf(0, 7));
		assertThat(PieceType.findPiece(0, 7)).isEqualTo(rook2);
		Rook rook3 = new Rook(WHITE, Point.valueOf(7, 0));
		assertThat(PieceType.findPiece(7, 0)).isEqualTo(rook3);
		Rook rook4 = new Rook(WHITE, Point.valueOf(7, 7));
		assertThat(PieceType.findPiece(7, 7)).isEqualTo(rook4);
	}

	@DisplayName("Rook의 가능한 방향 확인")
	@Test
	void checkRookPossibleMove() {
		Rook rook = new Rook(BLACK, Point.valueOf(4, 4));
		Empty empty = new Empty(NOTHING, Point.valueOf(5, 4));
		Empty empty2 = new Empty(NOTHING, Point.valueOf(4, 5));
		Empty empty3 = new Empty(NOTHING, Point.valueOf(3, 4));
		Empty empty4 = new Empty(NOTHING, Point.valueOf(4, 3));

		assertEquals(Optional.of(Direction.SOUTH), rook.direction(empty));
		assertEquals(Optional.of(Direction.EAST), rook.direction(empty2));
		assertEquals(Optional.of(Direction.NORTH), rook.direction(empty3));
		assertEquals(Optional.of(Direction.WEST), rook.direction(empty4));
	}

	@DisplayName("Rook의 불가능한 방향 확인")
	@Test
	void checkRookImpossibleMove() {
		Rook rook = new Rook(BLACK, Point.valueOf(4, 4));
		Empty empty = new Empty(NOTHING, Point.valueOf(3, 3));
		Empty empty2 = new Empty(NOTHING, Point.valueOf(5, 5));
		Empty empty3 = new Empty(NOTHING, Point.valueOf(5, 3));
		Empty empty4 = new Empty(NOTHING, Point.valueOf(3, 5));

		assertThatThrownBy(() -> rook.direction(empty))
			.isInstanceOf(IllegalArgumentException.class);

		assertThatThrownBy(() -> rook.direction(empty2))
			.isInstanceOf(IllegalArgumentException.class);

		assertThatThrownBy(() -> rook.direction(empty3))
			.isInstanceOf(IllegalArgumentException.class);

		assertThatThrownBy(() -> rook.direction(empty4))
			.isInstanceOf(IllegalArgumentException.class);
	}
}
