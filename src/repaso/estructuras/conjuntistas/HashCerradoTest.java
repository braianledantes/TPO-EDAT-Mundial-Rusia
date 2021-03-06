package repaso.estructuras.conjuntistas;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class HashCerradoTest {

    TablaHash<String> set;

    @Test
    void HashAbierto() {
        set = new TablaHashCerrado<>();
        assertTrue(set.esVacia());
        set.vaciar();
        assertTrue(set.esVacia());
        assertFalse(set.eliminar("sadasd"));
        assertFalse(set.pertenece("1"));
        assertFalse(set.pertenece(null));
        assertTrue(set.esVacia());
        assertTrue(set.insertar("1"));
        assertFalse(set.insertar("1"));
        assertFalse(set.insertar(null));
        assertTrue(set.pertenece("1"));
        assertFalse(set.esVacia());
        assertTrue(set.insertar("10001"));
        assertTrue(set.insertar("2"));
        assertTrue(set.insertar("-asdasd"));
        assertTrue(set.insertar("''¿sdn1--"));
        assertTrue(set.insertar("''¿sdn1--1"));
        assertFalse(set.insertar("2"));
        assertFalse(set.eliminar(null));
        assertTrue(set.eliminar("2"));
        assertTrue(set.insertar("2"));
    }
}