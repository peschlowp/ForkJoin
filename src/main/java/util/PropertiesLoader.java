/*
 * Copyright 2012 Patrick Peschlow
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License. 
 */

package util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

/**
 * Provides convenience methods for loading properties from a specified file and extracting certain
 * types of properties.
 * 
 * @author patrick.peschlow
 */
public class PropertiesLoader {

    public static Properties loadPropertiesFromFile(String filename) {
	Properties properties = new Properties();

	Path path = FileSystems.getDefault().getPath(filename);
	if (Files.exists(path)) {
	    try {
		InputStream fis = Files.newInputStream(path);
		properties.load(fis);
	    } catch (FileNotFoundException e) {
		throw new IllegalStateException("Could not find properties file at " + path, e);
	    } catch (IOException e) {
		throw new IllegalStateException("Could not load properties from file at " + path, e);
	    }
	}

	return properties;
    }

    public static String getStringProperty(Properties properties, String key) {
	return properties.getProperty(key);
    }

    public static Boolean getBooleanProperty(Properties properties, String key) {
	String value = properties.getProperty(key);
	if (value == null || value.isEmpty()) {
	    return null;
	}
	return Boolean.parseBoolean(value);
    }

    public static Long getLongProperty(Properties properties, String key) {
	String value = properties.getProperty(key);
	if (value == null || value.isEmpty()) {
	    return null;
	}
	return Long.parseLong(value);
    }

    public static Integer getIntegerProperty(Properties properties, String key) {
	String value = properties.getProperty(key);
	if (value == null || value.isEmpty()) {
	    return null;
	}
	return Integer.parseInt(value);
    }
}
