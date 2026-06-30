package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/googleLoginServlet")
public class googleLoginServlet extends HttpServlet {

    private static final String CLIENT_ID = "693877588588-13ftlfemmb71h04b5nn1ko1o9tj4a543.apps.googleusercontent.com";
    private static final String REDIRECT_URI = "http://localhost:8080/A/GoogleCallback";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String url = "https://accounts.google.com/o/oauth2/v2/auth"
                + "?client_id=" + CLIENT_ID
                + "&redirect_uri=" + REDIRECT_URI
                + "&response_type=code"
                + "&scope=email%20profile"
                + "&access_type=online";

        response.sendRedirect(url);
    }
}