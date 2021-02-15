package ru.netology.servlet;

import ru.netology.controller.PostController;
import ru.netology.repository.PostRepository;
import ru.netology.service.PostService;
import jakarta.servlet.*;
import jakarta.servlet.http.*;

import java.io.IOException;

public class MainServlet extends HttpServlet {
    // имитация POST и DELETE запросов осуществляется в классе Client
    private PostController controller;
    private static final String VALID_REQUEST_PATH = "/api/posts";
    private static final String VALID_REQUEST_PATH_ID = "/api/posts/\\d+";

    @Override
    public void init() {
        final var repository = new PostRepository();
        final var service = new PostService(repository);
        controller = new PostController(service);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        final var path = req.getRequestURI();
        if (path.equals(VALID_REQUEST_PATH)) {
            controller.all(resp);
        } else if (path.matches(VALID_REQUEST_PATH_ID)) {
            controller.getById(getId(path), resp);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        final var path = req.getRequestURI();
        if (path.matches(VALID_REQUEST_PATH_ID)) {
            controller.removeById(getId(path), resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        final var path = req.getRequestURI();
        if (path.equals(VALID_REQUEST_PATH)) {
            controller.save(req.getReader(), resp);
        }
    }

    private long getId(String path) {
        return Long.parseLong(path.substring(path.lastIndexOf("/")).replaceAll("/", ""));
    }
}

