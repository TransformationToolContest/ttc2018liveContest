package de.tudresden.inf.st.ttc18live.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import de.tudresden.inf.st.ttc18live.jastadd.model.ASTNode;
import de.tudresden.inf.st.ttc18live.jastadd.model.List;

import java.io.IOException;

public class ListSerializer extends StdSerializer<List> {

  public ListSerializer() {
    this(null);
  }

  public ListSerializer(Class<List> t) {
    super(t);
  }

  @Override
  public void serialize(
    List value, JsonGenerator jgen, SerializerProvider provider)
    throws IOException {

    jgen.writeStartObject();
    jgen.writeStringField("k", "List");
    jgen.writeArrayFieldStart("c");
    // unchecked cast, because otherwise class clash when adding serializer
    for (ASTNode child : ((List<ASTNode>) value).astChildren()) {
      provider.defaultSerializeValue(child, jgen);
    }
    jgen.writeEndArray();
    jgen.writeEndObject();
  }
}
