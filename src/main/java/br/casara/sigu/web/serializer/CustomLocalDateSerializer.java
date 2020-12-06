package br.casara.sigu.web.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CustomLocalDateSerializer extends StdSerializer<LocalDate> {

  private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

  public CustomLocalDateSerializer() {
    this(null);
  }

  public CustomLocalDateSerializer(final Class<LocalDate> clazz) {
    super(clazz);
  }

  @Override
  public void serialize(
    final LocalDate value,
    final JsonGenerator gen,
    final SerializerProvider provider
  ) throws IOException {
    gen.writeString(FORMATTER.format(value));
  }

}
