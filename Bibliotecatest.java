import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.*;

public class Bibliotecatest {

    private Biblioteca biblioteca;
    private Biblioteca.Libro libro1;
    private Biblioteca.Libro libro2;
    private Biblioteca.Libro libro3;
    private Biblioteca.Usuario usuario1;
    private Biblioteca.Usuario usuario2;

    @Before
    public void setUp() {
        biblioteca = new Biblioteca();

        libro1 = new Biblioteca.Libro("La Odisea", "Homero", "ISBN123", "Aventuras", 10);
        libro2 = new Biblioteca.Libro("1984", "George Orwell", "ISBN456", "Ciencia Ficci�n", 14);
        libro3 = new Biblioteca.Libro("Romeo y Julieta", "William Shakespeare", "ISBN789", "Rom�ntica", 12);

        usuario1 = new Biblioteca.Usuario("Juan", "P�rez", "G�mez", "12345678A", LocalDate.of(2000, 5, 10));
        usuario2 = new Biblioteca.Usuario("Mar�a", "L�pez", "Garc�a", "87654321B", LocalDate.of(2005, 7, 20));
    }

    @Test
    public void testAgregarLibro() {
        biblioteca.agregarLibro(libro1);
        biblioteca.agregarLibro(libro2);
        biblioteca.agregarLibro(libro3);

        assertEquals(3, biblioteca.listarLibrosDisponibles().size());
    }

    @Test
    public void testEliminarLibro() {
        biblioteca.agregarLibro(libro1);
        biblioteca.agregarLibro(libro2);
        biblioteca.agregarLibro(libro3);

        biblioteca.eliminarLibro(libro2);

        assertEquals(2, biblioteca.listarLibrosDisponibles().size());
        assertFalse(biblioteca.listarLibrosDisponibles().contains(libro2));
    }

    @Test
    public void testAgregarUsuario() {
        biblioteca.agregarUsuario(usuario1);
        biblioteca.agregarUsuario(usuario2);

        assertEquals(2, biblioteca.listarUsuarios().size());
    }

    @Test
    public void testEliminarUsuario() {
        biblioteca.agregarUsuario(usuario1);
        biblioteca.agregarUsuario(usuario2);

        biblioteca.eliminarUsuario(usuario2);

        assertEquals(1, biblioteca.listarUsuarios().size());
        assertFalse(biblioteca.listarUsuarios().contains(usuario2));
    }

    @Test
    public void testPrestarLibro() {
        biblioteca.agregarLibro(libro1);
        biblioteca.agregarLibro(libro2);
        biblioteca.agregarUsuario(usuario1);

        try {
            biblioteca.prestarLibro(libro1, usuario1);
        } catch (Exception e) {
            fail("Excepci�n inesperada: " + e.getMessage());
        }

        assertTrue(libro1.isPrestado());
        assertEquals(usuario1, libro1.getUsuario());
    }

    @Test
    public void testPrestarLibroConUsuarioNoCumpleEdad() {
        biblioteca.agregarLibro(libro1);
        biblioteca.agregarUsuario(usuario2);

        try {
            biblioteca.prestarLibro(libro1, usuario2);
            fail("Se esperaba una excepci�n");
        } catch (Exception e) {
            assertEquals("El usuario no cumple con la edad recomendada para el libro", e.getMessage());
        }

        assertFalse(libro1.isPrestado());
        assertNull(libro1.getUsuario());
    }

    @Test
    public void testDevolverLibro() {
        biblioteca.agregarLibro(libro1);
        biblioteca.agregarUsuario(usuario1);

        try {
            biblioteca.prestarLibro(libro1, usuario1);
        } catch (Exception e) {
            fail("Excepci�n inesperada: " + e.getMessage());
        }

        biblioteca.devolverLibro(libro1);

        assertFalse(libro1.isPrestado());
        assertNull(libro1.getUsuario());
    }

    // Prueba adicional: listar libros por categor�a
    @Test
    public void testListarLibrosPorCategoria() {
        biblioteca.agregarLibro(libro1);
        biblioteca.agregarLibro(libro2);
        biblioteca.agregarLibro(libro3);

        assertEquals(2, biblioteca.listarLibrosPorCategoria("Aventuras").size());
        assertEquals(1, biblioteca.listarLibrosPorCategoria("Ciencia Ficci�n").size());
        assertEquals(0, biblioteca.listarLibrosPorCategoria("Terror").size());
    }
}

