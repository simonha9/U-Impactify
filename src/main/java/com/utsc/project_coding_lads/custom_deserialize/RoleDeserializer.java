package com.utsc.project_coding_lads.custom_deserialize;

import java.io.IOException;

import org.springframework.boot.json.JsonParseException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.utsc.project_coding_lads.domain.Role;

public class RoleDeserializer extends JsonDeserializer<Role> {

	@Override
	public Role deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
		ObjectCodec codec = p.getCodec();
		JsonNode node = codec.readTree(p);

		if (node.has("name") && node.size() == 1) {
			//System.out.println("Got here perfect");
			Role role = new Role();
			if (node.get("name").isNull()) {
				role.setName(null);
			} else {
				role.setName(node.get("name").asText().toUpperCase());
			}
			return role;
		} else if (node.toPrettyString().equals("{ }")) {
			//System.out.println("Got empty role");
			return null;
		}
		//System.out.println("Got throw exception");
		throw new JsonParseException();
	}

}
