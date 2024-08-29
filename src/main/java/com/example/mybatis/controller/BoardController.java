package com.example.mybatis.controller;

import com.example.mybatis.dto.BoardDto.*;
import com.example.mybatis.dto.CommentDto.*;
import com.example.mybatis.dto.UserDto.UserResponseDto;
import com.example.mybatis.service.BoardService;
import com.example.mybatis.service.CommentService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequiredArgsConstructor
@RequestMapping("/board")
@Log4j2
public class BoardController {

    private final BoardService boardService;
    private final CommentService commentService;

//    @GetMapping("/list")
//    public String listBoards(Model model) {
//        model.addAttribute("boards", boardService.getAllBoards());
//        return "board/list";
//    }

    @GetMapping("/list")
    public String listBoards(Model model) {
        model.addAttribute("boards", boardService.getAllBoardsWithCommentCount());
        return "board/list";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("board", new CreateBoardRequestDto());
        return "board/create";
    }

    @PostMapping("/create")
    public String createBoard(@ModelAttribute("board") CreateBoardRequestDto createRequest, HttpSession session) {
        UserResponseDto user = (UserResponseDto) session.getAttribute("user");
        if (user != null) {
            boardService.createBoard(createRequest, user.getId());
            return "redirect:/board/list";
        }
        return "redirect:/user/login";
    }

    //    @GetMapping("/{id}")
//    public String viewBoard(@PathVariable Long id, Model model) {
//        BoardResponseDto board = boardService.getBoardById(id);
//        if (board != null) {
//            model.addAttribute("board", board);
//            return "board/view";
//        }
//        return "redirect:/board/list";
//    }
    @GetMapping("/{id}")
    public String viewBoard(@PathVariable Long id, Model model) {
        BoardResponseDto board = boardService.getBoardById(id);
        if (board != null) {
            model.addAttribute("board", board);
            model.addAttribute("comments", commentService.getCommentsByBoardId(id));
            model.addAttribute("newComment", new CommentRequestDto());
            log.info("board: " + board);
            log.info("comments: " + commentService.getCommentsByBoardId(id));
            return "board/view";
        } else {
            // 게시글을 찾지 못했을 때의 처리
            return "redirect:/board/list";  // 또는 에러 페이지로 리다이렉트
        }
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model, HttpSession session) {
        BoardResponseDto board = boardService.getBoardById(id);
        UserResponseDto user = (UserResponseDto) session.getAttribute("user");
        if (board != null && user != null && board.getWriterNickname().equals(user.getNickname())) {
            model.addAttribute("board", board);
            return "board/edit";
        }
        return "redirect:/board/list";
    }

    @PostMapping("/{id}/edit")
    public String updateBoard(@PathVariable Long id, @ModelAttribute("board") UpdateBoardRequestDto updateRequest, HttpSession session) {
        UserResponseDto user = (UserResponseDto) session.getAttribute("user");
        BoardResponseDto board = boardService.getBoardById(id);
        if (user != null && board != null && board.getWriterNickname().equals(user.getNickname())) {
            boardService.updateBoard(id, updateRequest);
        }
        return "redirect:/board/" + id;
    }

    @PostMapping("/{id}/delete")
    public String deleteBoard(@PathVariable Long id, HttpSession session) {
        UserResponseDto user = (UserResponseDto) session.getAttribute("user");
        BoardResponseDto board = boardService.getBoardById(id);
        if (user != null && board != null && board.getWriterNickname().equals(user.getNickname())) {
            boardService.deleteBoard(id);
        }
        return "redirect:/board/list";
    }

    @PostMapping("/{id}/comment")
    public String addComment(@PathVariable Long id, @ModelAttribute CommentRequestDto commentRequest, HttpSession session) {
        UserResponseDto user = (UserResponseDto) session.getAttribute("user");
        if (user != null) {
            commentService.createComment(commentRequest, id, user.getId());
        }
        return "redirect:/board/" + id;
    }
}
