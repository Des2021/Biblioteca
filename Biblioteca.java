import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Biblioteca implements Serializable {
    private List<Libro> libros;
    private List<Usuario> usuarios;

    public Biblioteca() {
        this.libros = new ArrayList<>();
        this.usuarios = new ArrayList<>();
    }

    // Métodos para dar de alta/baja un libro

    public void agregarLibro(Libro libro) {
        libros.add(libro);
    }

    public void eliminarLibro(Libro libro) {
        libros.remove(libro);
    }

    // Métodos para dar de alta/baja un usuario

    public void agregarUsuario(Usuario usuario) {
        usuarios.add(usuario);
    }

    public void eliminarUsuario(Usuario usuario) {
        usuarios.remove(usuario);
    }

    // Método para prestar un libro

    public void prestarLibro(Libro libro, Usuario usuario) throws Exception {
        if (libro.isPrestado()) {
            throw new Exception("El libro ya está prestado.");
        }

        if (usuario.getEdad() < libro.getEdadRecomendada()) {
            throw new Exception("El usuario no cumple con la edad recomendada para el libro.");
        }

        libro.setPrestado(true);
        libro.setUsuario(usuario);
    }

    // Método para devolver un libro

    public void devolverLibro(Libro libro) {
        libro.setPrestado(false);
        libro.setUsuario(null);
    }

    // Métodos para listar libros

    public List<Libro> listarLibrosPorTitulo(String titulo) {
        List<Libro> resultado = new ArrayList<>();
        for (Libro libro : libros) {
            if (libro.getTitulo().equals(titulo)) {
                resultado.add(libro);
            }
        }
        return resultado;
    }

    public List<Libro> listarLibrosPorCategoria(String categoria) {
        List<Libro> resultado = new ArrayList<>();
        for (Libro libro : libros) {
            if (libro.getCategoria().equals(categoria)) {
                resultado.add(libro);
            }
        }
        return resultado;
    }

    public List<Libro> listarLibrosPrestados() {
        List<Libro> resultado = new ArrayList<>();
        for (Libro libro : libros) {
            if (libro.isPrestado()) {
                resultado.add(libro);
            }
        }
        return resultado;
    }

    public List<Libro> listarLibrosDisponibles() {
        List<Libro> resultado = new ArrayList<>();
        for (Libro libro : libros) {
            if (!libro.isPrestado()) {
                resultado.add(libro);
            }
        }
        return resultado;
    }

    public List<Libro> listarLibrosPorUsuario(Usuario usuario) {
        List<Libro> resultado = new ArrayList<>();
        for (Libro libro : libros) {
            if (libro.isPrestado() && libro.getUsuario().equals(usuario)) {
                resultado.add(libro);
            }
        }
        return resultado;
    }

    // Método para almacenar el estado de la biblioteca en un archivo

    public void guardarEstado(String archivo) {
        try (FileOutputStream fileStream = new FileOutputStream(archivo);
             ObjectOutputStream objectStream = new ObjectOutputStream(fileStream)) {
            objectStream.writeObject(this);
            System.out.println("Estado de la biblioteca guardado en '" + archivo + "'");
        } catch (IOException e) {
            System.out.println("Error al guardar el estado de la biblioteca: " + e.getMessage());
        }
    }

    // Clase Libro

    public static class Libro implements Serializable {
        private String titulo;
        private String autor;
        private String identificador;
        private String categoria;
        private int edadRecomendada;
        private boolean prestado;
        private Usuario usuario;

        public Libro(String titulo, String autor, String identificador, String categoria, int edadRecomendada) {
            this.titulo = titulo;
            this.autor = autor;
            this.identificador = identificador;
            this.categoria = categoria;
            this.edadRecomendada = edadRecomendada;
            this.prestado = false;
        }

        public String getTitulo() {
            return titulo;
        }

        public String getAutor() {
            return autor;
        }

        public String getIdentificador() {
            return identificador;
        }

        public String getCategoria() {
            return categoria;
        }

        public int getEdadRecomendada() {
            return edadRecomendada;
        }

        public boolean isPrestado() {
            return prestado;
        }

        public Usuario getUsuario() {
            return usuario;
        }

        public void setPrestado(boolean prestado) {
            this.prestado = prestado;
        }

        public void setUsuario(Usuario usuario) {
            this.usuario = usuario;
        }
    }

    // Clase Usuario

    public static class Usuario implements Serializable {
        private String nombre;
        private String apellido1;
        private String apellido2;
        private String dni;
        private LocalDate fechaNacimiento;

        public Usuario(String nombre, String apellido1, String apellido2, String dni, LocalDate fechaNacimiento) {
            this.nombre = nombre;
            this.apellido1 = apellido1;
            this.apellido2 = apellido2;
            this.dni = dni;
            this.fechaNacimiento = fechaNacimiento;
        }

        public String getNombre() {
            return nombre;
        }

        public String getApellido1() {
            return apellido1;
        }

        public String getApellido2() {
            return apellido2;
        }

        public String getDni() {
            return dni;
        }

        public LocalDate getFechaNacimiento() {
            return fechaNacimiento;
        }

        public int getEdad() {
            LocalDate fechaActual = LocalDate.now();
            return fechaActual.getYear() - fechaNacimiento.getYear();
        }
    }

    public static void main(String[] args) {
        Biblioteca biblioteca = new Biblioteca();

        // Ejemplo de uso
        Libro libro1 = new Libro("La Odisea", "Homero", "ISBN123", "Aventuras", 10);
        Libro libro2 = new Libro("1984", "George Orwell", "ISBN456", "Ciencia Ficción", 14);
        Libro libro3 = new Libro("Romeo y Julieta", "William Shakespeare", "ISBN789", "Romántica", 12);

        Usuario usuario1 = new Usuario("Juan", "Pérez", "Gómez", "12345678A", LocalDate.of(2000, 5, 10));
        Usuario usuario2 = new Usuario("María", "López", "García", "87654321B", LocalDate.of(2005, 7, 20));

        biblioteca.agregarLibro(libro1);
        biblioteca.agregarLibro(libro2);
        biblioteca.agregarLibro(libro3);

        biblioteca.agregarUsuario(usuario1);
        biblioteca.agregarUsuario(usuario2);

        try {
            biblioteca.prestarLibro(libro1, usuario1);
            biblioteca.prestarLibro(libro2, usuario2);
        } catch (Exception e) {
            System.out.println("Error al prestar libro: " + e.getMessage());
        }

        System.out.println("Libros prestados: ");
        List<Libro> librosPrestados = biblioteca.listarLibrosPrestados();
        for (Libro libro : librosPrestados) {
            System.out.println(libro.getTitulo() + " - Prestado a: " + libro.getUsuario().getNombre());
        }

        System.out.println("Libros disponibles: ");
        List<Libro> librosDisponibles = biblioteca.listarLibrosDisponibles();
        for (Libro libro : librosDisponibles) {
            System.out.println(libro.getTitulo());
        }

        biblioteca.devolverLibro(libro1);

        System.out.println("Libros prestados después de devolver un libro: ");
        librosPrestados = biblioteca.listarLibrosPrestados();
        for (Libro libro : librosPrestados) {
            System.out.println(libro.getTitulo() + " - Prestado a: " + libro.getUsuario().getNombre());
        }

        // Guardar estado de la biblioteca en un archivo
        biblioteca.guardarEstado("Bibliotecaesp.dat");
    }
}


