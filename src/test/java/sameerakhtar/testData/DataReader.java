package sameerakhtar.testData;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.io.FileUtils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class DataReader {

	public static List<HashMap<String, Object>> getJsonDataToMap(String filePath) throws IOException {
		// Read JSON to String
		String jsonContent = FileUtils.readFileToString(new File(filePath), StandardCharsets.UTF_8);
		// String to HashMap Jackson
		ObjectMapper mapper = new ObjectMapper();
		List<HashMap<String, Object>> data = mapper.readValue(jsonContent,
				new TypeReference<List<HashMap<String, Object>>>() {
				});
		return data;
	}
}
