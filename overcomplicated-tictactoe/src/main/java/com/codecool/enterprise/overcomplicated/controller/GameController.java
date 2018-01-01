package com.codecool.enterprise.overcomplicated.controller;

import com.codecool.enterprise.overcomplicated.model.Player;
import com.codecool.enterprise.overcomplicated.model.TictactoeGame;
import com.codecool.enterprise.overcomplicated.service.AvatarService;
import com.codecool.enterprise.overcomplicated.service.ComicsService;
import com.codecool.enterprise.overcomplicated.service.FunFactService;
import com.codecool.enterprise.overcomplicated.service.TictactoeAIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

@Controller
@SessionAttributes({"player", "avatar_uri"})
public class GameController {

    @Autowired
    ComicsService comicsService;

    @Autowired
    FunFactService funFactService;

    @Autowired
    AvatarService avatarService;

    @Autowired
    TictactoeAIService tictactoeAIService;

    @ModelAttribute("player")
    public Player getPlayer() {
        return new Player();
    }

    @ModelAttribute("avatar_uri")
    public String getAvatarUri() {
        return avatarService.getRandomAvatar();
    }

    @GetMapping(value = "/")
    public String welcomeView(@ModelAttribute Player player) {
        return "welcome";
    }

    @PostMapping(value="/changeplayerusername")
    public String changPlayerUserName(@ModelAttribute Player player) {
        return "redirect:/game";
    }

    @GetMapping(value = "/game")
    public String gameView(@ModelAttribute("player") Player player, Model model,
                           @RequestParam(value = "start", required=false) String start,
                           HttpServletRequest httpServletRequest) {

        model.addAttribute("funfact", funFactService.getRandomChuckNorrisJoke());
        model.addAttribute("comic_uri", comicsService.getRandomComics());

        TictactoeGame tictactoeGame = (TictactoeGame) httpServletRequest.getSession().getAttribute("game");

        if(tictactoeGame == null) {
            TictactoeGame tictactoeGameStart = new TictactoeGame();
            if (Objects.equals("ai", start)) {
                aiStart(tictactoeGameStart);
            }
            if (Objects.equals("user", start)) {
                tictactoeGameStart.initializeBoard();
            }
            model.addAttribute("tictactoegame", tictactoeGameStart);
            httpServletRequest.getSession().setAttribute("game", tictactoeGameStart);
        } else {
            if (Objects.equals("ai", start)) {
                aiStart(tictactoeGame);
            }
            if (Objects.equals("user", start)) {
                tictactoeGame.initializeBoard();
            }
            model.addAttribute("tictactoegame", tictactoeGame);
        }

        return "game";
    }

    @GetMapping(value = "/game-move")
    public String gameMove(@ModelAttribute("player") Player player, @ModelAttribute("move") int move,
                           HttpServletRequest httpServletRequest) {

        TictactoeGame tictactoeGame = (TictactoeGame) httpServletRequest.getSession().getAttribute("game");
        tictactoeGame.placeMark(move);

        if (tictactoeGame.checkForWin()) {
            getWinner(tictactoeGame, httpServletRequest);
            return "redirect:/game-end";
        }

        String stringBoard = tictactoeAIService.convertArrayBoardToString(tictactoeGame.getBoard());
        int aiMove = tictactoeAIService.getAIstep(stringBoard);
        tictactoeGame.placeMark(aiMove);

        if (tictactoeGame.checkForWin()) {
            getWinner(tictactoeGame, httpServletRequest);
            return "redirect:/game-end";
        }

        if (tictactoeGame.isBoardFull()) {
            httpServletRequest.getSession().setAttribute("winmessage", "draw");
            return "redirect:/game-end";
        }

        httpServletRequest.getSession().setAttribute("game", tictactoeGame);

        return "redirect:/game";
    }

    @GetMapping(value = "game-end")
    public String gameEnd(HttpServletRequest httpServletRequest,
                          @ModelAttribute("player") Player player,
                          Model model) {
        String winMessage = (String) httpServletRequest.getSession().getAttribute("winmessage");
        httpServletRequest.getSession().invalidate();
        model.addAttribute("winMessage" , winMessage);
        return "game-end";
    }

    private void aiStart(TictactoeGame tictactoeGame) {
        TictactoeAIService tictactoeAIService = new TictactoeAIService();
        String stringBoard = tictactoeAIService.convertArrayBoardToString(tictactoeGame.getBoard());
        int aiMove = tictactoeAIService.getAIstep(stringBoard);
        tictactoeGame.placeMark(aiMove);
    }

    private void getWinner(TictactoeGame tictactoeGame, HttpServletRequest httpServletRequest) {
        String winMessage;
        if (Objects.equals(tictactoeGame.getLastPlaceMark(), "o")) {
            winMessage = "O won";
        } else {
            winMessage = "X won";
        }
        httpServletRequest.getSession().setAttribute("winmessage", winMessage);

    }

}