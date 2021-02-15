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
    private static final String VALID_REQUEST_PATH_ID = "/api/posts/";
    private static final String GET = "GET";
    private static final String POST = "POST";
    private static final String DELETE = "DELETE";

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
        } else if (path.matches(VALID_REQUEST_PATH_ID + "\\d+")) {
            controller.getById(getId(path), resp);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        final var path = req.getRequestURI();
        if (path.matches(VALID_REQUEST_PATH_ID + "\\d+")) {
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

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) {
        try {
            final var method = req.getMethod();
            if (method.equals(GET)) {
                doGet(req, resp);
                return;
            }

            if (method.equals(POST)) {
                doPost(req, resp);
                return;
            }

            if (method.equals(DELETE)) {
                doDelete(req, resp);
                return;
            }
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    private long getId(String path) {
        return Long.parseLong(path.substring(path.lastIndexOf("/")).replaceAll("/", ""));
    }
}

