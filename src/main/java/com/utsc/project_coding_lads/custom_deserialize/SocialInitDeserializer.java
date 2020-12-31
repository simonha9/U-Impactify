package com.utsc.project_coding_lads.custom_deserialize;

import java.io.IOException;

import org.springframework.boot.json.JsonParseException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.utsc.project_coding_lads.domain.SocialInitiative;

public class SocialInitDeserializer extends JsonDeserializer<SocialInitiative> {

	@Override
	public SocialInitiative deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
		ObjectCodec codec = p.getCodec();
		JsonNode node = codec.readTree(p);

		if (node.has("name") && node.size() == 1) {
			//System.out.println("Got here perfect");
			SocialInitiative socialInit = new SocialInitiative();
			if (node.get("name").isNull()) {
				socialInit.setName(null);
			} else {
				socialInit.setName(node.get("name").asText());
			}
			return socialInit;
		} else if (node.toPrettyString().equals("{ }")) {
			//System.out.println("Got empty social");
			return null;
		}
		
		//System.out.println("Got exception");
		throw new JsonParseException();
	}

}
