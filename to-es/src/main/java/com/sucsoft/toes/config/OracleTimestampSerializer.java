package com.sucsoft.toes.config;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
/*import oracle.sql.TIMESTAMP;*/
/**
 * Created with IntelliJ IDEA.
 *
 * @author Libin
 * extends JsonSerializer<TIMESTAMP>
 */
@Component
public class OracleTimestampSerializer  {

   /* @Override
    public void serialize(TIMESTAMP timestamp, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        Timestamp tt = null;
        try {
            tt = timestamp.timestampValue();
            Date date = new Date(tt.getTime());
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            jsonGenerator.writeString(dateFormat.format(date));
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }*/
}
