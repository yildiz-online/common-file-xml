/*
 *
 * This file is part of the Yildiz-Engine project, licenced under the MIT License  (MIT)
 *
 * Copyright (c) 2019 Grégory Van den Borre
 *
 * More infos available: https://engine.yildiz-games.be
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 * documentation files (the "Software"), to deal in the Software without restriction, including without
 * limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 * of the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial
 *  portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 *  WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS
 * OR COPYRIGHT  HOLDERS BE LIABLE FOR ANY CLAIM,
 *  DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE  SOFTWARE.
 *
 *
 */

package be.yildizgames.common.file.xml;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

class XMLSerializerTest {

    @Test
    void happyFlow() {
        XMLSerializer serializer = new XMLSerializer(Paths.get("test.xml"));
        Assertions.assertNotNull(serializer);
    }

    @Test
    void withNullParameter() {
        Assertions.assertThrows(NullPointerException.class, () -> new XMLSerializer(null));
    }

    @Nested
    class ReadFromFile {

        @Test
        void happyFlow() throws IOException {
            Path dir = Files.createTempDirectory("xml");
            Path file = dir.resolve("test.xml");
            XMLSerializer<DummyXml> serializer = new XMLSerializer<>(file);
            serializer.writeToFile(new DummyXml());
            DummyXml xml = serializer.readFromFile();
            Assertions.assertEquals("value", xml.getValue());
            Assertions.assertEquals("ok", xml.getTest());
        }

        @Test
        void fileNotExisting() {
            XMLSerializer<DummyXml> serializer = new XMLSerializer<>(Paths.get("notexists.xml"));
            Assertions.assertThrows(IllegalStateException.class, serializer::readFromFile);
        }

    }

    @Nested
    class WriteToFile {

        @Test
        void happyFlow() throws IOException {
            Path dir = Files.createTempDirectory("xml");
            Path file = dir.resolve("test.xml");
            XMLSerializer<DummyXml> serializer = new XMLSerializer<>(file);
            serializer.writeToFile(new DummyXml());
            Assertions.assertTrue(Files.size(file) > 0);
        }
    }
}
