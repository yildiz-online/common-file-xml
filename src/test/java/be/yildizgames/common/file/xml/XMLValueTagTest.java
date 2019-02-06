/*
 * This file is part of the Yildiz-Engine project, licenced under the MIT License  (MIT)
 *
 *  Copyright (c) 2019 Grégory Van den Borre
 *
 *  More infos available: https://engine.yildiz-games.be
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 *  documentation files (the "Software"), to deal in the Software without restriction, including without
 *  limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 *  of the Software, and to permit persons to whom the Software is furnished to do so,
 *  subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in all copies or substantial
 *  portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 *  WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS
 *  OR COPYRIGHT  HOLDERS BE LIABLE FOR ANY CLAIM,
 *  DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE  SOFTWARE.
 *
 */

package be.yildizgames.common.file.xml;


import be.yildizgames.common.exception.implementation.ImplementationException;
import be.yildizgames.common.file.exception.FileCorruptionException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author Grégory Van den Borre
 */
public final class XMLValueTagTest {

    @Test
    public void testGenerate() {
        XMLTag tag = new XMLValueTag("a name", "a value");
        String result = "<a name>a value</a name>";
        assertEquals(result, tag.generate(new StringBuilder()));
    }

    @Test
    public void testXMLValueTagName() {
        XMLTag tag = new XMLValueTag("name", "value");
        assertEquals("name", tag.getName());
    }

    @Test
    public void testXMLValueTagName1() {
        assertThrows(FileCorruptionException.class, () -> new XMLValueTag("a<a", "value"));
    }

    @Test
    public void testXMLValueTagName2() {
        assertThrows(FileCorruptionException.class, () -> new XMLValueTag("a>a", "value"));
    }

    @Test
    public void testXMLValueTagName3() {
        assertThrows(FileCorruptionException.class, () -> new XMLValueTag("a&a", "value"));
    }

    @Test
    public void testXMLValueTagName4() {
        assertThrows(FileCorruptionException.class, () -> new XMLValueTag("a'a", "value"));
    }

    @Test
    public void testXMLValueTagName5() {
        assertThrows(FileCorruptionException.class, () -> new XMLValueTag("a\"a", "value"));
    }

    @Test
    public void testXMLValueTagNameNull() {
        assertThrows(ImplementationException.class, () -> new XMLValueTag(null, "value"));
    }

    @Test
    public void testXMLValueTagValue() {
        assertThrows(ImplementationException.class, () -> new XMLValueTag("name", null));
    }

    @Test
    public void testXMLValueTagValue1() {
        assertThrows(FileCorruptionException.class, () -> new XMLValueTag("name", "a<a"));
    }

    @Test
    public void testXMLValueTagValue2() {
        assertThrows(FileCorruptionException.class, () -> new XMLValueTag("name", "a>a"));
    }

    @Test
    public void testXMLValueTagValue3() {
        assertThrows(FileCorruptionException.class, () -> new XMLValueTag("name", "a&a"));
    }

    @Test
    public void testXMLValueTagValue4() {
        assertThrows(FileCorruptionException.class, () -> new XMLValueTag("name", "a'a"));
    }

    @Test
    public void testXMLValueTagValue5() {
        assertThrows(FileCorruptionException.class, () -> new XMLValueTag("name", "a\"a"));
    }

}
