package BTSEvents;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Deserializer;

import java.util.Map;

public class BTSEventDeserializer implements Deserializer<SomeBTSEvent> {

    @Override public void close() {

    }

    @Override public void configure(Map<String, ?> arg0, boolean arg1) {

    }

    @Override
    public SomeBTSEvent deserialize(String arg0, byte[] arg1) {
        ObjectMapper mapper = new ObjectMapper();
        SomeBTSEvent btsEvent = null;
        try {
            btsEvent = mapper.readValue(arg1, SomeBTSEvent.class);
        } catch (Exception e) {

            e.printStackTrace();
        }
        return btsEvent;
    }

}
