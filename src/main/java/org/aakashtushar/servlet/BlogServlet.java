package org.aakashtushar.servlet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

@WebServlet(
        urlPatterns = "/blog"
)
public class BlogServlet extends HttpServlet {
}
