package jerarquicas;

import lineales.Cola;
import lineales.ColaDinamica;
import lineales.Lista;
import lineales.ListaDinamica;

import java.util.Objects;

/**
 * Un árbol binariorio es aquel árbol donde cada nodo tiene, como máximo, dos hijos.
 *
 * @param <T> Tipo de dato que almacena el árbol
 */
public class ArbolBinario<T> implements Arbol<T> {
    public static final int HIJO_IZQUIERDO = 0;
    public static final int HIJO_DERECHO = 1;

    private Nodo<T> raiz;

    /**
     * Crea un arbol binario vacio.
     */
    public ArbolBinario() {
        this.raiz = null;
    }

    /**
     * Dado un elemento elemNuevo y un elemento elemPadre, inserta elemNuevo como hijo izquierdo o derecho
     * de la primer aparición de elemPadre, según lo indique el parámetro posicion. Para que la operación termine
     * con éxito debe existir un nodo en el árbol con elemento = elemPadre y ese nodo debe tener libre su hijo
     * posicion.
     *
     * @param elemNuevo elemento a insertar
     * @param elemPadre elmento padre del nodo
     * @param posicion  HIJO_IZQUIERDO o HIJO_DERECHO
     * @return si puede realizar la inserción devuelve verdadero, en caso contrario devuelve falso
     * @throws IllegalArgumentException si la posicion es invalida
     */
    public boolean insertar(T elemNuevo, T elemPadre, int posicion) throws IllegalArgumentException {
        boolean exito = true;
        Nodo<T> nodoPadre;

        if (elemNuevo != null) {
            if (raiz == null) {
                raiz = new Nodo<>(elemNuevo);
            } else {
                nodoPadre = obtenerNodo(elemPadre, raiz);
                if (nodoPadre != null) {
                    if (posicion == HIJO_IZQUIERDO && !nodoPadre.tieneHijoIzq()) {
                        nodoPadre.setHijoIzq(new Nodo<>(elemNuevo));
                    } else if (posicion == HIJO_DERECHO && !nodoPadre.tieneHijoDer()) {
                        nodoPadre.setHijoDer(new Nodo<>(elemNuevo));
                    } else {
                        exito = false;
                    }
                }
            }
        } else {
            exito = false;
        }

        return exito;
    }

    /**
     * Dado un elemento elemNuevo y un elemento elemPadre, inserta elemNuevo desde el hijo izquierdo al hijo derecho.
     * Para insertar solo el hijo derecho y no el izquierdo, se debe insertar primero el izquierdo como nulo y luego
     * el derecho.
     *
     * @param elemNuevo elemento a insertar
     * @param elemPadre elmento padre del nodo
     * @return si puede realizar la inserción devuelve verdadero, en caso contrario devuelve falso
     */
    @Override
    public boolean insertar(T elemNuevo, T elemPadre) {
        boolean exito = true;
        Nodo<T> nodoPadre;

        if (elemNuevo != null) {
            if (raiz == null) {
                raiz = new Nodo<>(elemNuevo);
            } else {
                nodoPadre = obtenerNodo(elemPadre, raiz);
                if (nodoPadre != null) {
                    if (!nodoPadre.tieneHijoIzq()) {
                        nodoPadre.setHijoIzq(new Nodo<>(elemNuevo));
                    } else if (!nodoPadre.tieneHijoDer()) {
                        nodoPadre.setHijoDer(new Nodo<>(elemNuevo));
                    } else {
                        exito = false;
                    }
                }
            }
        } else {
            exito = false;
        }

        return exito;
    }

    private Nodo<T> obtenerNodo(T elem, Nodo<T> nodo) {
        Nodo<T> result = null;

        if (nodo != null) {
            if (nodo.getElem().equals(elem)) {
                result = nodo;
            } else {
                result = obtenerNodo(elem, nodo.getHijoIzq());
                if (result == null) {
                    result = obtenerNodo(elem, nodo.getHijoDer());
                }
            }
        }

        return result;
    }

    @Override
    public boolean esVacio() {
        return raiz == null;
    }

    @Override
    public int altura() {
        return altura(raiz);
    }

    private int altura(Nodo<T> nodo) {
        int result = -1, altIzq, altDer;

        if (nodo != null) {
            altIzq = altura(nodo.getHijoIzq());
            altDer = altura(nodo.getHijoDer());
            result = Math.max(altIzq, altDer) + 1;
        }

        return result;
    }

    @Override
    public int nivel(T elemento) {
        return nivel(elemento, raiz);
    }

    private int nivel(T elemento, Nodo<T> nodo) {
        int nivel = -1;

        if (elemento != null && nodo != null) {
            if (elemento.equals(nodo.getElem())) {
                nivel = 0;
            } else {
                nivel = nivel(elemento, nodo.getHijoIzq());
                if (nivel == -1) {
                    nivel = nivel(elemento, nodo.getHijoDer());
                }
                if (nivel != -1) {
                    nivel++;
                }
            }
        }

        return nivel;
    }

    @Override
    public T padre(T elemento) {
        return padre(elemento, raiz, null);
    }

    private T padre(T elemento, Nodo<T> nodoActual, Nodo<T> nodoPadre) {
        T result = null;

        if (elemento != null && nodoActual != null) {
            if (elemento.equals(nodoActual.getElem())) {
                if (nodoPadre != null) {
                    result = nodoPadre.getElem();
                }
            } else {
                result = padre(elemento, nodoActual.getHijoIzq(), nodoActual);
                if (result == null) {
                    result = padre(elemento, nodoActual.getHijoDer(), nodoActual);
                }
            }
        }

        return result;
    }

    @Override
    public Lista<T> listarPreorden() {
        Lista<T> lista = new ListaDinamica<>();
        listarPreorden(lista, raiz);
        return lista;
    }

    private void listarPreorden(Lista<T> lista, Nodo<T> nodo) {
        if (nodo != null) {
            lista.insertar(nodo.getElem());
            listarPreorden(lista, nodo.getHijoIzq());
            listarPreorden(lista, nodo.getHijoDer());
        }
    }

    @Override
    public Lista<T> listarInorden() {
        Lista<T> lista = new ListaDinamica<>();
        listarInorden(lista, raiz);
        return lista;
    }

    private void listarInorden(Lista<T> lista, Nodo<T> nodo) {
        if (nodo != null) {
            listarInorden(lista, nodo.getHijoIzq());
            lista.insertar(nodo.getElem());
            listarInorden(lista, nodo.getHijoDer());
        }
    }

    @Override
    public Lista<T> listarPosorden() {
        Lista<T> lista = new ListaDinamica<>();
        listarPosorden(lista, raiz);
        return lista;
    }

    private void listarPosorden(Lista<T> lista, Nodo<T> nodo) {
        if (nodo != null) {
            listarPosorden(lista, nodo.getHijoIzq());
            listarPosorden(lista, nodo.getHijoDer());
            lista.insertar(nodo.getElem());
        }
    }

    @Override
    public Lista<T> listarNiveles() {
        Lista<T> lista = new ListaDinamica<>();
        Cola<Nodo<T>> cola;
        Nodo<T> nodoActual;

        if (raiz != null) {
            cola = new ColaDinamica<>();
            cola.poner(raiz);
            while (!cola.esVacia()) {
                nodoActual = cola.obtenerFrente();
                cola.sacar();
                lista.insertar(nodoActual.getElem());
                if (nodoActual.tieneHijoIzq()) {
                    cola.poner(nodoActual.getHijoIzq());
                }
                if (nodoActual.tieneHijoDer()) {
                    cola.poner(nodoActual.getHijoDer());
                }
            }
        }

        return lista;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ArbolBinario<T> that = (ArbolBinario<T>) o;
        return equals(this.raiz, that.raiz);
    }

    private boolean equals(Nodo<T> nodothis, Nodo<T> nodoThat) {
        boolean equals = false;

        if (nodothis == null && nodoThat == null) {
            equals = true;
        } else if (nodothis != null && nodoThat != null) {
            if (nodothis.getElem().equals(nodoThat.getElem())) {
                equals = equals(nodothis.getHijoIzq(), nodoThat.getHijoIzq()) &&
                        equals(nodothis.getHijoDer(), nodoThat.getHijoDer());
            }
        }

        return equals;
    }

    @Override
    public Object clone() {
        ArbolBinario<T> clon = new ArbolBinario<>();
        clon.raiz = clone(this.raiz);
        return clon;
    }

    private Nodo<T> clone(Nodo<T> nodo) {
        Nodo<T> nodoNuevo = null;
        if (nodo != null) {
            nodoNuevo = new Nodo<>(nodo.getElem());
            nodoNuevo.setHijoIzq(clone(nodo.getHijoIzq()));
            nodoNuevo.setHijoDer(clone(nodo.getHijoDer()));
        }
        return nodoNuevo;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("ArbolBinario{");
        toString(sb, raiz);
        sb.append("}");
        return sb.toString();
    }

    private void toString(StringBuilder sb, Nodo<T> nodo) {
        if (nodo != null) {
            sb.append(nodo.getElem()).append(", ");
            toString(sb, nodo.getHijoIzq());
            toString(sb, nodo.getHijoDer());
        }
    }

    @Override
    public void vaciar() {
        raiz = null;
    }

    private static class Nodo<T> {
        private T elem;
        private Nodo<T> hijoIzq, hijoDer;

        public Nodo(T elem, Nodo<T> hijoIzq, Nodo<T> hijoDer) {
            this.elem = elem;
            this.hijoIzq = hijoIzq;
            this.hijoDer = hijoDer;
        }

        public Nodo(T elem) {
            this.elem = elem;
            this.hijoIzq = this.hijoDer = null;
        }

        public T getElem() {
            return elem;
        }

        public void setElem(T elem) {
            this.elem = elem;
        }

        public Nodo<T> getHijoIzq() {
            return hijoIzq;
        }

        public void setHijoIzq(Nodo<T> hijoIzq) {
            this.hijoIzq = hijoIzq;
        }

        public Nodo<T> getHijoDer() {
            return hijoDer;
        }

        public void setHijoDer(Nodo<T> hijoDer) {
            this.hijoDer = hijoDer;
        }

        public boolean tieneHijoIzq() {
            return hijoIzq != null;
        }

        public boolean tieneHijoDer() {
            return hijoDer != null;
        }

        @Override
        public String toString() {
            return "Nodo{" +
                    "elem=" + elem +
                    '}';
        }
    }
}