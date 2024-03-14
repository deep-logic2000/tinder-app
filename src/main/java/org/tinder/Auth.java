package org.tinder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Optional;

public class Auth {
    public static String cookieName = "TINDER";

    private static final Cookie[] empty = new Cookie[]{};

    private static final RuntimeException EX = new RuntimeException("cookie is expected to exist... (probably you should add filter before the servlet)");

    private static Cookie[] ensureNotNull(Cookie[] cs) {
        return cs == null ? empty : cs;
    }

    public static Optional<String> getCookieValue(HttpServletRequest rq) {
        return Arrays.stream(ensureNotNull(rq.getCookies()))
                .filter(c -> c.getName().equals(cookieName))
                .findFirst()
                .map(Cookie::getValue);
    }

    public static String getCookieValueUnsafe(HttpServletRequest rq) {
        return getCookieValue(rq)
                .orElseThrow(() -> EX);
    }

    public static void setCookieValue(String cookieValue, HttpServletResponse rs) {
        Cookie cookie = new Cookie(cookieName, cookieValue);
        cookie.setMaxAge(10 * 60); // seconds, 10 min
        rs.addCookie(cookie);
    }

    public static void clearCookie(HttpServletResponse rs) {
        Cookie cookie = new Cookie(cookieName, "");
        cookie.setMaxAge(0);
        rs.addCookie(cookie);
    }

    public static void renderUnregistered(HttpServletResponse resp) {
        resp.setStatus(401);
    }
}
