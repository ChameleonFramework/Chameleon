/*
 * Chameleon - Cross-platform Minecraft plugin creation library
 *  Copyright (c) 2021 SLLCoding <luisjk266@gmail.com>
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy
 *  of this software and associated documentation files (the "Software"), to deal
 *  in the Software without restriction, including without limitation the rights
 *  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  copies of the Software, and to permit persons to whom the Software is
 *  furnished to do so, subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in all
 *  copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 *  SOFTWARE.
 */

package dev.hypera.chameleon.core.configuration;

import dev.hypera.chameleon.core.Chameleon;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class YamlConfiguration implements Configuration {

    private File file;
    private Map<String, Object> config;

    private static final Yaml yaml = new Yaml();

    public YamlConfiguration(Chameleon chameleon, String filename, boolean copyDefaultFromResources) {
        try {
            File dataFolder = chameleon.getDataFolder();
            if (!dataFolder.exists()) dataFolder.mkdirs();
            this.file = new File(dataFolder, filename);
            if (!file.exists()) {
                if (copyDefaultFromResources) {
                    // TODO: Copy from resources.
                } else file.createNewFile();
            }
            FileReader reader = new FileReader(file);
            config = yaml.load(reader);
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public <T> T get(String path, Class<T> type) {
        return type.cast(config.get(path));
    }

    @Override
    public Object get(String path) {
        return config.get(path);
    }

    @Override
    public String getString(String path) {
        return (String) config.get(path);
    }

    @Override
    public int getInt(String path) {
        return (int) config.get(path);
    }

    @Override
    public long getLong(String path) {
        return (long) config.get(path);
    }

    @Override
    public boolean getBoolean(String path) {
        return (boolean) config.get(path);
    }

    @Override
    public List<?> getList(String path) {
        return (List<?>) config.get(path);
    }

}
