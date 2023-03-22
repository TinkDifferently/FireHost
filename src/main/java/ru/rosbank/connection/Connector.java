package ru.rosbank.connection;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;
import ru.chrome.ext.api.Session;
import ru.chrome.ext.api.session.SessionData;
import ru.chrome.ext.toApp.FireServer;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class Connector {

    private static final Map<String, Connector> connectors = new HashMap<>();

    private final Session session;
    private final FireServer server;

    public static Connector getInstance(@NotNull String session) {
        return connectors.computeIfAbsent(session,Connector::new);
    }

    private Connector(String session) {
        this.session = Session.create(session);
        server = new FireServer("C:\\Users\\tinkd\\AppData\\Roaming\\Inist\\FireWyrmNativeMessageHost.proxy.exe",
                "polkfpcimpfhcflopocaobbbnphilcbi");
        server.run();
    }

    @SneakyThrows
    public String parseResponse(String id) {
        var login = SessionData.getInstance().<String>$(id);
        var msg = new ObjectMapper()
                .readTree(login)
                .get("msg")
                .asText();
        return new ObjectMapper().readTree(msg)
                .get(1)
                .asText();
    }

    public void sendMessage(String initialMessage, Path path) {
        server.mountObserver(initialMessage, path, session);
    }
}
