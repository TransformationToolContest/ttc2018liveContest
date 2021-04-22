package de.tudresden.inf.st.ttc18live.serializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import de.tudresden.inf.st.ttc18live.jastadd.model.*;

import java.io.File;
import java.io.IOException;

public class JsonSerializer {

  public static void write(SocialNetwork s, String fileName) {
    writeAstNode(s, fileName);
  }

  private static void writeAstNode(ASTNode a, String fileName) {
    ObjectMapper mapper = new ObjectMapper();
    mapper.enable(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS);
    mapper.enable(SerializationFeature.INDENT_OUTPUT);

    SimpleModule module = new SimpleModule();
    module.addSerializer(ASTNode.class, new ASTNodeSerializer());
    module.addSerializer(List.class, new ListSerializer());
    module.addSerializer(Opt.class, new OptSerializer());
    mapper.registerModule(module);

    try {
      mapper.writeValue(new File(fileName), a);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
