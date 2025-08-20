package web;

import bank.Bank;
import utils.Json;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.*;
import java.util.stream.Collectors;

public class WebServer {
    private final Bank bank;

    public WebServer(Bank bank) { this.bank = bank; }

    public void start(int port) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);

        server.createContext("/accounts", this::handleAccounts);
        server.createContext("/transfer", this::handleTransfer);

        server.setExecutor(null);
        server.start();
        System.out.println("HTTP server started on http://localhost:" + port);
    }

    private void handleAccounts(HttpExchange ex) throws IOException {
        if (!"GET".equalsIgnoreCase(ex.getRequestMethod())) { respond(ex, 405, "Method Not Allowed"); return; }

        var list = bank.listAccounts().stream().map(a -> Map.<String,Object>of(
                "id", a.getAccountId(),
                "number", a.getAccountNumber(),
                "type", a.getType(),
                "balance", a.getBalance(),
                "customerId", a.getCustomerId()
        )).collect(Collectors.toList());

        respond(ex, 200, Json.arr(list));
    }

    private void handleTransfer(HttpExchange ex) throws IOException {
        if (!"POST".equalsIgnoreCase(ex.getRequestMethod())) { respond(ex, 405, "Method Not Allowed"); return; }
        // Very simple form: from=...&to=...&amount=...
        String body = new String(ex.getRequestBody().readAllBytes());
        Map<String,String> form = parseForm(body);
        try {
            long from = Long.parseLong(form.getOrDefault("from","-1"));
            long to = Long.parseLong(form.getOrDefault("to","-1"));
            double amt = Double.parseDouble(form.getOrDefault("amount","0"));
            bank.transfer(from, to, amt);
            respond(ex, 200, Json.obj(Map.of("status","ok")));
        } catch (Exception e) {
            respond(ex, 400, Json.obj(Map.of("status","error","message", e.getMessage())));
        }
    }

    private static Map<String,String> parseForm(String body) {
        Map<String,String> map = new HashMap<>();
        for (String p : body.split("&")) {
            if (p.isBlank()) continue;
            String[] kv = p.split("=",2);
            map.put(urlDecode(kv[0]), kv.length>1? urlDecode(kv[1]) : "");
        }
        return map;
    }
    private static String urlDecode(String s) {
        try { return java.net.URLDecoder.decode(s, java.nio.charset.StandardCharsets.UTF_8); }
        catch (Exception e) { return s; }
    }

    private static void respond(HttpExchange ex, int code, String payload) throws IOException {
        ex.getResponseHeaders().add("Content-Type", "application/json");
        ex.sendResponseHeaders(code, payload.getBytes().length);
        try (OutputStream os = ex.getResponseBody()) { os.write(payload.getBytes()); }
    }
}
