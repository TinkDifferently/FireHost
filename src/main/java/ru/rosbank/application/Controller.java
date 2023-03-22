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
        Connector.getInstance().sendMessage(initialMessage, Path.of("sign.txt"));
        return Connector.getInstance().parseResponse("login");
    }

    @PostMapping(value = "/sign/accountRequest", consumes = "application/json; charset=utf-8", produces = "text/plain; charset=utf-8")
    public String accountRequest(@RequestBody String data) throws JsonProcessingException {

        var tree = new ObjectMapper().readTree(data);

        var requestData="{\"cmdId\":25,\"type\":\"cmd\",\"c\":1,\"n\":1,\"colonyId\":0,\"msg\":\"[\\\"Invoke\\\",0,6,\\\"\\\"," + tree.get("msg").asText() + "]\",\"ext\":\"polkfpcimpfhcflopocaobbbnphilcbi\"}";

        var initialMessage = "{\"cmdId\":20,\"type\":\"cmd\",\"c\":1,\"n\":1,\"colonyId\":0,\"msg\":\"[\\\"GetP\\\",1,0,\\\"SetPluginProperties\\\"]\",\"ext\":\"polkfpcimpfhcflopocaobbbnphilcbi\"}";
        SessionData.getInstance().$("session", "{\"cmdId\":12,\"type\":\"resp\",\"c\":1,\"n\":1,\"colonyId\":0,\"msg\":\"[\\\"success\\\",{\\\"Session\\\":\\\"" + tree.get("session").asText() + "\\\",\\\"Command\\\":1,\\\"RememberPacket\\\":true}]\",\"ext\":\"polkfpcimpfhcflopocaobbbnphilcbi\"}");
        SessionData.getInstance().$("request-data", requestData);
        Connector.getInstance().sendMessage(initialMessage, Path.of("request.txt"));
        return Connector.getInstance().parseResponse("request");
    }

    @PostMapping(value = "/sign/json", consumes = "application/json; charset=utf-8", produces = "text/plain; charset=utf-8")
    public String json(@RequestBody String data) throws JsonProcessingException {
        var tree = new ObjectMapper().readTree(data);
        var result = tree.get("msg").asText();

        var expected = "[\\\"#digestVersion:5\\\\r\\\\n#modelVersion:1\\\\r\\\\nstatementRequest.accountGroupName:\\\\r\\\\nstatementRequest.accounts[0]:40702344287360000001\\\\r\\\\nstatementRequest.accounts[1]:40702344987360000000\\\\r\\\\nstatementRequest.accounts[2]:40702756487360000004\\\\r\\\\nstatementRequest.accounts[3]:40702756787360000005\\\\r\\\\nstatementRequest.accounts[4]:40702810087500000951\\\\r\\\\nstatementRequest.accounts[5]:40702810087870000500\\\\r\\\\nstatementRequest.accounts[6]:40702810096430003585\\\\r\\\\nstatementRequest.accounts[7]:40702810387360024673\\\\r\\\\nstatementRequest.accounts[8]:40702810487360000144\\\\r\\\\nstatementRequest.accounts[9]:40702810500000114727\\\\r\\\\nstatementRequest.accounts[10]:40702810687360001600\\\\r\\\\nstatementRequest.accounts[11]:40702810687364070200\\\\r\\\\nstatementRequest.accounts[12]:40702810696434070202\\\\r\\\\nstatementRequest.accounts[13]:40702810787870000703\\\\r\\\\nstatementRequest.accounts[14]:40702810887350000000\\\\r\\\\nstatementRequest.accounts[15]:40702810887870000687\\\\r\\\\nstatementRequest.accounts[16]:40702810987360001591\\\\r\\\\nstatementRequest.accounts[17]:40702826087360000004\\\\r\\\\nstatementRequest.accounts[18]:40702826387360000005\\\\r\\\\nstatementRequest.accounts[19]:40702840111111111111\\\\r\\\\nstatementRequest.accounts[20]:40702840222222222222\\\\r\\\\nstatementRequest.accounts[21]:40702840287360000385\\\\r\\\\nstatementRequest.accounts[22]:40702840687360000257\\\\r\\\\nstatementRequest.accounts[23]:40702840800003014727\\\\r\\\\nstatementRequest.accounts[24]:40702840900000014727\\\\r\\\\nstatementRequest.accounts[25]:40702978087360000266\\\\r\\\\nstatementRequest.accounts[26]:40702978400003014727\\\\r\\\\nstatementRequest.accounts[27]:40702978500000014727\\\\r\\\\nstatementRequest.accounts[28]:40702978500000035127\\\\r\\\\nstatementRequest.accounts[29]:42102810687360014017\\\\r\\\\nstatementRequest.accounts[30]:42103810287010000000\\\\r\\\\nstatementRequest.accounts[31]:42103810487360000259\\\\r\\\\nstatementRequest.accounts[32]:42103810887360000260\\\\r\\\\nstatementRequest.accounts[33]:42104810087360000069\\\\r\\\\nstatementRequest.accounts[34]:42104810087360000072\\\\r\\\\nstatementRequest.accounts[35]:42104810087360000085\\\\r\\\\nstatementRequest.accounts[36]:42104810087360000098\\\\r\\\\nstatementRequest.accounts[37]:42104810087360000108\\\\r\\\\nstatementRequest.accounts[38]:42104810087360000137\\\\r\\\\nstatementRequest.accounts[39]:42104810087360000140\\\\r\\\\nstatementRequest.accounts[40]:42104810187360000079\\\\r\\\\nstatementRequest.accounts[41]:42104810187360000082\\\\r\\\\nstatementRequest.accounts[42]:42104810187360000105\\\\r\\\\nstatementRequest.accounts[43]:42104810187360000121\\\\r\\\\nstatementRequest.accounts[44]:42104810187360000134\\\\r\\\\nstatementRequest.accounts[45]:42104810287360000089\\\\r\\\\nstatementRequest.accounts[46]:42104810287360000092\\\\r\\\\nstatementRequest.accounts[47]:42104810287360000102\\\\r\\\\nstatementRequest.accounts[48]:42104810287360000144\\\\r\\\\nstatementRequest.accounts[49]:42104810387360000073\\\\r\\\\nstatementRequest.accounts[50]:42104810387360000086\\\\r\\\\nstatementRequest.accounts[51]:42104810387360000109\\\\r\\\\nstatementRequest.accounts[52]:42104810387360000138\\\\r\\\\nstatementRequest.accounts[53]:42104810387360000141\\\\r\\\\nstatementRequest.accounts[54]:42104810487360000067\\\\r\\\\nstatementRequest.accounts[55]:42104810487360000070\\\\r\\\\nstatementRequest.accounts[56]:42104810487360000083\\\\r\\\\nstatementRequest.accounts[57]:42104810487360000096\\\\r\\\\nstatementRequest.accounts[58]:42104810487360000106\\\\r\\\\nstatementRequest.accounts[59]:42104810487360000119\\\\r\\\\nstatementRequest.accounts[60]:42104810487360000122\\\\r\\\\nstatementRequest.accounts[61]:42104810487360000135\\\\r\\\\nstatementRequest.accounts[62]:42104810587360000077\\\\r\\\\nstatementRequest.accounts[63]:42104810587360000080\\\\r\\\\nstatementRequest.accounts[64]:42104810587360000093\\\\r\\\\nstatementRequest.accounts[65]:42104810587360000103\\\\r\\\\nstatementRequest.accounts[66]:42104810587360000145\\\\r\\\\nstatementRequest.accounts[67]:42104810687360000087\\\\r\\\\nstatementRequest.accounts[68]:42104810687360000090\\\\r\\\\nstatementRequest.accounts[69]:42104810687360000100\\\\r\\\\nstatementRequest.accounts[70]:42104810687360000139\\\\r\\\\nstatementRequest.accounts[71]:42104810687360000142\\\\r\\\\nstatementRequest.accounts[72]:42104810687870000001\\\\r\\\\nstatementRequest.accounts[73]:42104810787360000068\\\\r\\\\nstatementRequest.accounts[74]:42104810787360000084\\\\r\\\\nstatementRequest.accounts[75]:42104810787360000110\\\\r\\\\nstatementRequest.accounts[76]:42104810787360000123\\\\r\\\\nstatementRequest.accounts[77]:42104810787360000136\\\\r\\\\nstatementRequest.accounts[78]:42104810887360000065\\\\r\\\\nstatementRequest.accounts[79]:42104810887360000078\\\\r\\\\nstatementRequest.accounts[80]:42104810887360000081\\\\r\\\\nstatementRequest.accounts[81]:42104810887360000104\\\\r\\\\nstatementRequest.accounts[82]:42104810987360000075\\\\r\\\\nstatementRequest.accounts[83]:42104810987360000101\\\\r\\\\nstatementRequest.accounts[84]:42104810987360000143\\\\r\\\\nstatementRequest.accounts[85]:42105810187360000010\\\\r\\\\nstatementRequest.accounts[86]:42105810287020000001\\\\r\\\\nstatementRequest.accounts[87]:42105810687360000002\\\\r\\\\nstatementRequest.accounts[88]:42105810687360000015\\\\r\\\\nstatementRequest.accounts[89]:42106810997970000000\\\\r\\\\nstatementRequest.accounts[90]:47422810297970000010\\\\r\\\\nstatementRequest.accounts[91]:47423810087930000065\\\\r\\\\nstatementRequest.clientId:524376874\\\\r\\\\nstatementRequest.clientName:Публичное акционерное общество \\\\\\\"Новолипецкий металлургический комбинат\\\\\\\"\\\\r\\\\nstatementRequest.dateFrom:2023-03-19T00:00:00+03:00\\\\r\\\\nstatementRequest.dateTo:2023-03-19T00:00:00+03:00\\\\r\\\\nstatementRequest.id:435\\\\r\\\\nstatementRequest.requestDate:2023-03-20T14:25:08+03:00\\\\r\\\\nstatementRequest.requestedByUser:529481417\\\",\\\"Подпись данных\\\"]";


        return expected.startsWith(result) ? "S_OK" : "S_ERROR";
    }

}