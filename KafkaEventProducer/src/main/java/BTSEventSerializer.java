import com.fasterxml.jackson.databind.ObjectMapper;
import com.motek.btsAnalisys.BTSEvents.BTSEvent;
import com.motek.btsAnalisys.BTSEvents.SomeBTSEvent;
import org.apache.kafka.common.serialization.Serializer;

import java.util.Map;

public class BTSEventSerializer implements Serializer<BTSEvent> {
    @Override
    public void configure(Map configs, boolean isKey) {

    }

    //@Override
    public byte[] serialize(String topic, SomeBTSEvent btsEvent) {
        byte[] retVal = null;
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            retVal = objectMapper.writeValueAsString(btsEvent).getBytes();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return retVal;
    }


    @Override
    public byte[] serialize(String topic, BTSEvent data) {
        return new byte[0];
    }

    @Override
    public void close() {

    }
}
