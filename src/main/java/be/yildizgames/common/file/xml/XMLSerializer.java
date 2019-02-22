/*
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
 * portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS
 * OR COPYRIGHT  HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE  SOFTWARE.
 */

package be.yildizgames.common.file.xml;


import be.yildizgames.common.exception.implementation.ImplementationException;
import be.yildizgames.common.file.Serializer;
import be.yildizgames.common.file.exception.FileCorruptionException;
import be.yildizgames.common.file.exception.FileCreationException;
import be.yildizgames.common.file.exception.FileMissingException;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Use XML to serialize and deserialize objects.
 *
 * @param <T> Object type to use.
 * @author Grégory Van Den Borre
 */
public final class XMLSerializer<T> implements Serializer<T> {

    /**
     * File to use to read/write object.
     */
    private final Path file;

    /**
     * Full constructor.
     *
     * @param xmlFile Initialize the file to use.
     */
    public XMLSerializer(final Path xmlFile) {
        super();
        ImplementationException.throwForNull(xmlFile);
        this.file = xmlFile;
    }

    /**
     * Throw an exception with file not found.
     */
    private void fileNotFound() {
        throw new FileMissingException(this.file + "was not found");
    }

    /**
     * Read a file and deserialize object from it.
     *
     * @return The created object.
     */
    @Override
    @SuppressWarnings("unchecked")
    public T readFromFile() {
        InputStream fis = null;
        try {
            if (Files.notExists(this.file)) {
                this.fileNotFound();
            }
            fis = Files.newInputStream(this.file);
        } catch (FileNotFoundException e) {
            this.fileNotFound();
        } catch (IOException e) {
            throw new FileCorruptionException("XML configuration file corrupted.");
        }
        try (XMLDecoder decode = new XMLDecoder(fis)) {
            decode.setExceptionListener(e -> {
                throw new FileCorruptionException("XML configuration file corrupted.");
            });
            return (T) decode.readObject();
        }
    }

    /**
     * Write an object into a XML file.
     *
     * @param o Object to persist.
     */
    @Override
    public void writeToFile(final T o) {
        try (OutputStream fos = Files.newOutputStream(this.file)) {
            try (XMLEncoder encoder = new XMLEncoder(fos)) {
                encoder.setExceptionListener(FileCreationException::new);
                encoder.writeObject(o);
            }
        } catch (IOException e) {
            throw new FileCreationException(e);
        }
    }

}
