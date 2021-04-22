package de.tudresden.inf.st.ttc18live.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import de.tudresden.inf.st.ttc18live.jastadd.model.ASTNode;
import de.tudresden.inf.st.ttc18live.jastadd.model.Opt;

import java.io.IOException;

public class OptSerializer extends StdSerializer<Opt> {

  public OptSerializer() {
    this(null);
  }

  public OptSerializer(Class<Opt> t) {
    super(t);
  }

  @Override
  public void serialize(
    Opt value, JsonGenerator jgen, SerializerProvider provider)
    throws IOException {

    jgen.writeStartObject();
    jgen.writeStringField("k", "Opt");
    if (value.getNumChild() > 0) {
      jgen.writeFieldName("c");
      // unchecked cast, because otherwise class clash when adding serializer
      for (ASTNode child : ((Opt<ASTNode>) value).astChildren()) {
        provider.defaultSerializeValue(child, jgen);
      }
    }
    jgen.writeEndObject();
  }
}
