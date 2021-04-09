package chess.service;

import static chess.domain.Status.*;
import static chess.domain.piece.Color.*;

import chess.dao.ChessDao;
import chess.dao.SQLConnection;
import chess.domain.ChessGame;
import chess.domain.board.Point;
import chess.domain.piece.Color;
import chess.dto.BoardDto;
import chess.dto.RequestDto;
import chess.dto.UserDto;

public class ChessService {
    private final ChessDao chessDAO;

    public ChessService() {
        chessDAO = new ChessDao(new SQLConnection());
    }

    public void restartChess(UserDto userDto) {
        String userId = chessDAO.findUserIdByUser(userDto);
        chessDAO.deleteBoard(userId);
    }

    public ChessGame matchBoard(UserDto userDto) {
        String userId = chessDAO.findUserIdByUser(userDto);
        BoardDto boardDto = chessDAO.findBoard(userId);
        Color color = chessDAO.findBoardNextTurn(userId);
        return makeChessGame(boardDto, color);
    }

    public String matchBoardImageSource(ChessGame chessGame, String requestBody) {
        return chessGame.getBoard().get(Point.of(requestBody)).getName();
    }

    public String matchPieceName(ChessGame chessGame, String requestBody) {
        return chessGame.getBoard().get(Point.of(requestBody)).getName();
    }

    private ChessGame makeChessGame(BoardDto boardDto, Color color) {
        if (boardDto == null) {
            return new ChessGame();
        }
        return new ChessGame(boardDto.getBoard(), color);
    }

    public void addBoard(ChessGame chessGame, UserDto userDto, String requestBody) {
        chessDAO.addBoard(chessDAO.findUserIdByUser(userDto), requestBody, makeNextColor(chessGame));
    }

    public String makeNextColor(ChessGame chessGame) {
        if (chessGame.nextTurn().isSameAs(BLACK)) {
            return WHITE.name();
        }
        return BLACK.name();
    }

    public String makeCurrentColor(ChessGame chessGame, String requestBody) {
        if (chessGame.getBoard().get(Point.of(requestBody)).isSameTeam(BLACK)) {
            return BLACK.name();
        }
        return WHITE.name();
    }

    public int move(UserDto userDto, ChessGame chessGame, RequestDto requestDto) {
        String source = requestDto.getSource();
        String target = requestDto.getTarget();
        try {
            chessGame.playTurn(Point.of(source), Point.of(target));
            if (chessGame.isEnd()) {
                chessDAO.saveBoard(chessDAO.findUserIdByUser(userDto), chessGame, makeNextColor(chessGame));
                return RESET_CONTENT.code();
            }
            chessDAO.saveBoard(chessDAO.findUserIdByUser(userDto), chessGame, makeNextColor(chessGame));
            return OK.code();
        } catch (UnsupportedOperationException | IllegalArgumentException e) {
            return NO_CONTENT.code();
        }
    }

    public double score(UserDto userDto, String requestBody) {
        return matchBoard(userDto).calculateScore(Color.valueOf(requestBody)).getScore();
    }

    public void addUser(String requestName, String requestPassword) {
        UserDto userDto = new UserDto(requestName, requestPassword);
        chessDAO.addUser(userDto);
    }

    public UserDto requestLoginUser(String requestName, String requestPassword) {
        return new UserDto(requestName, requestPassword);
    }

    public UserDto findUser(UserDto userDto) {
        return chessDAO.findByUserNameAndPwd(userDto.getName(), userDto.getPwd());
    }
}
