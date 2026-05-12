package org.design_system.Persistence;

import java.io.*;

public class IOFilePersistence implements  FilePersistance{

    //Obtengo el directorio actual
    private final String currentDir = System.getProperty("user.dir");

    //defino los subdirectorios (desde el directorio actual) donde voy a almacenar el archivo I/O
    //File.separator obtiene el tipo de separador (/, \) especifico del SO
    private final String storedDir = File.separator + "managerFiles" + File.separator + "IO" + File.separator;

    //variable para almacenar el nombre del arcivo
    private final String fileName;


    //constructor
    public IOFilePersistence(String fileName) throws IOException {
        //al crear la instancia se asigna el nombre del archivo a la variable fileName
        this.fileName = fileName;

        //intancia de File que obtiene el path de las sub carpetas
        var file = new File(currentDir + storedDir);
        //si no existe se crean
        if (!file.exists() && !file.mkdirs()) throw new IOException("Failed to create directory: " + file.getAbsolutePath());


        //clearFile();

    }

    @Override
    public String write(final String data) {
        //Try-with-resources:
        //Los recursos dentro del try(...) se cierran automáticamente al salir del bloque, incluso si hay excepción.
        try(
                //crea fisicament en el disco duro el archivo fileName en la ruta especificada
                //appenend = true, para escribir al final del archivo sin borrar su contenido
                //appened = false, sobre escribe el archivo eliminando la escritura anterior
                var FileWriter = new FileWriter(currentDir + storedDir + fileName, true);

                //Agrega un buffer en memoria para escribir en lotes (más eficiente).
                var bufferedWriter = new BufferedWriter(FileWriter);
                //Permite escribir texto con println(), print(), printf().
                var printerWriter = new PrintWriter(bufferedWriter);
        ){

            //escribo los datos (data) en el archivo fisico
            printerWriter.println(data);

        }catch(IOException e){
            e.printStackTrace();
        }

        return data;
    }

    @Override
    public String findAll() {
        var content = new StringBuilder();
        try(var reader = new BufferedReader(new FileReader(currentDir + storedDir + fileName));){

            String line;

            do{
                line = reader.readLine();
                if((line != null)) content.append(line)
                        .append(System.lineSeparator());

            }while (line != null);
        }catch(IOException e){
            e.printStackTrace();
        }

        return content.toString();
    }

    public void clearFile(){

        try (OutputStream outputStream = new FileOutputStream(new File(currentDir + storedDir + fileName));)
        {
            System.out.println("File cleared successfully.");
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }


}
