package ru.rosbank.application;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.chrome.ext.api.session.SessionData;
import ru.rosbank.connection.Connector;

import java.nio.file.Path;

@RestController
public class Controller {

    @GetMapping("/status")
    public String status() {
        return "S_OK";
    }

    @PostMapping(value = "/sign/login", consumes = "application/json; charset=utf-8", produces = "text/plain; charset=utf-8")
    public String login(@RequestBody String data) throws JsonProcessingException {
        var session = new ObjectMapper().readTree(data)
                .get("session")
                .asText();
        var initialMessage = "{\"cmd\":\"create\",\"mimetype\":\"application/x-bicryptplugin2\",\"ext\":\"polkfpcimpfhcflopocaobbbnphilcbi\"}";
        SessionData.getInstance().$("session", "{\"cmdId\":10,\"type\":\"resp\",\"c\":1,\"n\":1,\"colonyId\":0,\"msg\":\"[\\\"success\\\",{\\\"Session\\\":\\\"" + session + "\\\",\\\"Command\\\":1,\\\"RememberPacket\\\":false}]\",\"ext\":\"polkfpcimpfhcflopocaobbbnphilcbi\"}");
        Connector.getInstance(session).sendMessage(initialMessage, Path.of("sign.txt"));
        return Connector.getInstance(session).parseResponse("login");
    }

    @PostMapping(value = "/sign/accountRequest", consumes = "application/json; charset=utf-8", produces = "text/plain; charset=utf-8")
    public String accountRequest(@RequestBody String data) throws JsonProcessingException {

        var tree = new ObjectMapper().readTree(data);
        var session=tree.get("session").asText();

        var requestData="{\"cmdId\":25,\"type\":\"cmd\",\"c\":1,\"n\":1,\"colonyId\":0,\"msg\":\"[\\\"Invoke\\\",0,6,\\\"\\\"," + tree.get("msg").asText() + "]\",\"ext\":\"polkfpcimpfhcflopocaobbbnphilcbi\"}";

        var initialMessage = "{\"cmdId\":20,\"type\":\"cmd\",\"c\":1,\"n\":1,\"colonyId\":0,\"msg\":\"[\\\"GetP\\\",1,0,\\\"SetPluginProperties\\\"]\",\"ext\":\"polkfpcimpfhcflopocaobbbnphilcbi\"}";
        SessionData.getInstance().$("session", "{\"cmdId\":12,\"type\":\"resp\",\"c\":1,\"n\":1,\"colonyId\":0,\"msg\":\"[\\\"success\\\",{\\\"Session\\\":\\\"" + session + "\\\",\\\"Command\\\":1,\\\"RememberPacket\\\":true}]\",\"ext\":\"polkfpcimpfhcflopocaobbbnphilcbi\"}");
        SessionData.getInstance().$("request-data", requestData);
        Connector.getInstance(session).sendMessage(initialMessage, Path.of("request.txt"));
        return Connector.getInstance(session).parseResponse("request");
    }

}
