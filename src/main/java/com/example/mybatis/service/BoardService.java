package com.example.mybatis.service;

import com.example.mybatis.mapper.BoardMapper;
import com.example.mybatis.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.example.mybatis.domain.Board;
import com.example.mybatis.dto.BoardDto.*;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final UserMapper userMapper;
    private final BoardMapper boardMapper;

    public BoardResponseDto createBoard(CreateBoardRequestDto createRequest, Long writerId) {
        Board board = Board.builder()
                .title(createRequest.getTitle())
                .content(createRequest.getContent())
                .writerId(writerId)
                .build();

        boardMapper.insert(board);
        return convertToBoardResponse(boardMapper.findById(board.getId()));
    }

    public BoardResponseDto getBoardById(Long id) {
        Board board = boardMapper.findById(id);
        if (board != null) {
            BoardResponseDto responseDto = convertToBoardResponse(board);
            // 작성자의 닉네임을 가져와 설정
            String writerNickname = userMapper.getNicknameById(board.getWriterId());
            responseDto.setWriterNickname(writerNickname);
            return responseDto;
        }
        return null;
    }

    public List<BoardResponseDto> getAllBoards() {
        List<Board> boards = boardMapper.findAll();
        return boards.stream()
                .map(this::convertToBoardResponse)
                .collect(Collectors.toList());
    }

    public List<BoardResponseDto> getAllBoardsWithCommentCount() {
        List<Board> boards = boardMapper.findAllWithCommentCount();
        return boards.stream()
                .map(this::convertToBoardResponse)
                .collect(Collectors.toList());
    }

    public BoardResponseDto updateBoard(Long id, UpdateBoardRequestDto updateRequest) {
        Board existingBoard = boardMapper.findById(id);
        if (existingBoard != null) {
            existingBoard.setTitle(updateRequest.getTitle());
            existingBoard.setContent(updateRequest.getContent());
            boardMapper.update(existingBoard);
            return convertToBoardResponse(boardMapper.findById(id));
        }
        return null;
    }

    public boolean deleteBoard(Long id) {
        return boardMapper.softDelete(id) > 0;
    }

    private BoardResponseDto convertToBoardResponse(Board board) {
        BoardResponseDto responseDto = new BoardResponseDto();
        responseDto.setId(board.getId());
        responseDto.setTitle(board.getTitle());
        responseDto.setContent(board.getContent());
        responseDto.setWriterNickname(board.getWriterNickname());
        responseDto.setCreatedAt(board.getCreatedAt());
        responseDto.setCommentCount(board.getCommentCount());
        return responseDto;
    }
}