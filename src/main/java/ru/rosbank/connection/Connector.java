package ru.rosbank.connection;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import ru.chrome.ext.api.session.SessionData;
import ru.chrome.ext.toApp.FireServer;

import java.nio.file.Path;

public class Connector {
    private static final Connector instance = new Connector();
    private final FireServer server;

    public static Connector getInstance() {
        return instance;
    }

    public Connector() {
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
       server.mountObserver(initialMessage,path);
    }
}
