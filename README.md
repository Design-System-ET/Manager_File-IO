# Manipulación de Archivos con Java I/O
## Guía de Estudio

---

## 1. ¿Qué es Java I/O?

**Java I/O (Input/Output)** es un paquete (`java.io`) de Java que proporciona clases para manejar la entrada y salida de datos. Es decir, todo lo relacionado con leer y escribir archivos, redes, consola, etc.

### ¿Para qué sirve?

- **Leer archivos** del disco duro
- **Escribir archivos** en el disco duro
- **Leer datos de la consola** (teclado)
- **Escribir datos en la consola** (pantalla)
- **Comunicación por red** (sockets)
- **Manipular flujo de datos** en general

### Clasificación de Clases

Java I/O se divide en dos categorías principales:

| Tipo | Descripción | Ejemplos |
|------|-------------|----------|
| **Streams de bytes** | Para datos binarios (imágenes, audio) | `InputStream`, `OutputStream` |
| **Streams de caracteres** | Para texto (caracteres) | `Reader`, `Writer` |

Además, las clases se dividen en:

| Tipo | Descripción | Ejemplos |
|------|-------------|----------|
| **Clases de nodo** | Interactúan directamente con la fuente | `FileReader`, `FileWriter` |
| **Clases de envolvente (wrapper)** | Agregan funcionalidad (buffer, formato) | `BufferedReader`, `PrintWriter` |

### Excepciones

Todas las operaciones de I/O pueden lanzar `IOException` (o subclases), por lo que debe manejarse con `try-catch` o propagarse con `throws`.

---

## 2. Estructura del Proyecto

```
src/main/java/org/design_system/
├── Main.java                      → Punto de entrada (usa la librería)
└── Persistence/
    ├── FilePersistance.java       → Interfaz (define el contrato)
    └── IOFilePersistence.java    → Implementación (manipula archivos)
```

---

## 3. Flujo de Escritura (write)

```
datos → PrintWriter → BufferedWriter → FileWriter → archivo
```

```java
public String write(final String data) {
    try(
        var FileWriter = new FileWriter(ruta, true);
        var bufferedWriter = new BufferedWriter(FileWriter);
        var printerWriter = new PrintWriter(bufferedWriter);
    ){
        printerWriter.println(data);
    } catch(IOException e) { e.printStackTrace(); }
    return data;
}
```

### Qué hace cada clase:

| Clase | Función |
|-------|---------|
| **FileWriter** | Escribe caracteres en el archivo. El `true` significa "añadir al final" (append). |
| **BufferedWriter** | Agrega un buffer en memoria para escribir en lotes (más eficiente). |
| **PrintWriter** | Permite escribir texto con `println()`, `print()`, `printf()`. |

### Try-with-resources:
Los recursos dentro del `try(...)` se cierran automáticamente al salir del bloque, incluso si hay excepción.

---

## 4. Flujo de Lectura (findAll)

```
archivo → FileReader → BufferedReader → aplicación
```

```java
public String findAll() {
    var content = new StringBuilder();
    try(var reader = new BufferedReader(new FileReader(ruta))) {
        String line;
        do {
            line = reader.readLine();
            if(line != null) content.append(line).append(System.lineSeparator());
        } while (line != null);
    } catch(IOException e) { e.printStackTrace(); }
    return content.toString();
}
```

### Qué hace cada clase:

| Clase | Función |
|-------|---------|
| **FileReader** | Lee caracteres del archivo. |
| **BufferedReader** | Agrega buffer para leer en lotes (más eficiente). Además provee `readLine()`. |

### Lectura línea por línea:
- `readLine()` devuelve cada línea (sin salto de línea) o `null` si llegó al final.
- Usamos `StringBuilder` para construir el resultado de forma eficiente.

---

## 5. Conceptos Clave del Código

### Constructor - Crear directorio si no existe

```java
var file = new File(ruta);
if (!file.exists() && !file.mkdirs()) 
    throw new IOException("Failed to create directory");
```

- `file.exists()` → ¿existe?
- `file.mkdirs()` → crea la carpeta y las carpetas intermedias necesarias.

### Rutas del sistema

```java
String currentDir = System.getProperty("user.dir");        // Directorio actual
String storedDir = File.separator + "carpeta" + File.separator;  // Separador del SO
```

- `System.getProperty("user.dir")` → obtiene el directorio de trabajo del proyecto.
- `File.separator` → detecta automáticamente `/` (Linux/Mac) o `\` (Windows).

---

## 6. Resumen Visual

```
ESCRITURA (write):
┌──────────┐   ┌─────────────┐   ┌─────────────┐   ┌────────┐
│  datos   │ → │  PrintWriter │ → │ BufferedWriter│ → │  File  │
└──────────┘   └─────────────┘   └─────────────┘   └────────┘

LECTURA (findAll):
┌────────┐   ┌─────────────┐   ┌─────────────┐   ┌──────────┐
│  File  │ → │   FileReader  │ → │ BufferedReader│ → │  datos   │
└────────┘   └─────────────┘   └─────────────┘   └──────────┘
```

---

## 7. Puntos Clave para Recordar

1. **FileWriter(ruta, true)** → el `true` es modo append (añadir), `false` sobrescribe.
2. **Try-with-resources** → siempre usar para cerrar archivos automáticamente.
3. **Clases Wrapper (Buffered)** → mejoran rendimiento, no son obligatorias pero recomendadas.
4. **readLine()** → devuelve `null` al llegar al final del archivo.
5. **File.separator** → portable entre sistemas operativos.

---

## 8. Ejecución en Main

```java
FilePersistance persistance = new IOFilePersistence("test.csv");
persistance.write("maria; rodrigo; 234235");
System.out.println(persistance.findAll());
```

Resultado en el archivo `test.csv`:
```
maria; rodrigo; 234235
```