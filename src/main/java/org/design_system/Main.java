package org.design_system;

import org.design_system.Persistence.FilePersistance;
import org.design_system.Persistence.IOFilePersistence;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {

        /*referencia la interface, instancia solo accede a los metodos definidos en ella*/
        FilePersistance persistance = new IOFilePersistence("test.csv");

        persistance.write("maria; rodrigo; 234235");

        //System.out.println(persistance.findAll());

        /*instancia de clase, accede a los metodos de la interface y los metodos propios si no son privados*/
        //IOFilePersistence persis =  new IOFilePersistence("test.csv");
        //persis.clearFile();

    }

}
