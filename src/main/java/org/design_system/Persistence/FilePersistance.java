package org.design_system.Persistence;

public interface FilePersistance {

    String write(final String data);

    String findAll();
}