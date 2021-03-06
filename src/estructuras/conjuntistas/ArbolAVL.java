package estructuras.conjuntistas;

public class ArbolAVL<T extends Comparable<T>> extends ABB<T> {

    protected boolean insertar(T elem, Nodo<T> nodo) {
        boolean exito = super.insertar(elem, nodo);

        if (exito) {
            nodo.recalcularAltura();
            balancear(nodo);
        }

        return exito;
    }

    @Override
    protected boolean eliminar(T elem, Nodo<T> nodo, Nodo<T> nodoPadre) {
        boolean exito = super.eliminar(elem, nodo, nodoPadre);
        if (exito) {
            nodo.recalcularAltura();
            balancear(nodo);
        }
        return exito;
    }

    private void balancear(Nodo<T> nodo) {
        int balance = calcularBalance(nodo);
        int balanceHijo;

        if (balance == -2) { // inclinado hacia la derecha
            balanceHijo = calcularBalance(nodo.getDer());
            if (balanceHijo == 0 || balanceHijo == -1) {
                rotarIzquierda(nodo);
            } else if (balanceHijo == 1) {
                rotarDerecha(nodo.getDer());
                rotarIzquierda(nodo);
            }
        } else if (balance == 2) { // inclinado hacia la izquierda
            balanceHijo = calcularBalance(nodo.getIzq());
            if (balanceHijo == 0 || balanceHijo == 1) {
                rotarDerecha(nodo);
            } else if (balanceHijo == -1) {
                rotarIzquierda(nodo.getIzq());
                rotarDerecha(nodo);
            }
        }
    }

    private int calcularBalance(Nodo<T> nodo) {
        int altIzq = -1, altDer = -1;
        if (nodo.tieneIzq())
            altIzq = nodo.getIzq().getAltura();
        if (nodo.tieneDer())
            altDer = nodo.getDer().getAltura();
        return altIzq - altDer;
    }

    private void rotarIzquierda(Nodo<T> nodoRaiz) {
        Nodo<T> nodoTemp = nodoRaiz.getDer();
        // intercambio elementos
        T elemTemp = nodoRaiz.getElem();
        nodoRaiz.setElem(nodoTemp.getElem());
        nodoTemp.setElem(elemTemp);
        // le asigno a la raiz el hijo derecho del hijo derecho de la raiz
        nodoRaiz.setDer(nodoRaiz.getDer().getDer());
        // acomodo los enlaces al nodo temporal
        nodoTemp.setDer(nodoTemp.getIzq());
        nodoTemp.setIzq(nodoRaiz.getIzq());
        // cambio el hijo izquierdo de la raiz
        nodoRaiz.setIzq(nodoTemp);

        nodoTemp.recalcularAltura();
        nodoRaiz.recalcularAltura();
    }

    private void rotarDerecha(Nodo<T> nodoRaiz) {
        Nodo<T> nodoTemp = nodoRaiz.getIzq();
        // intercambio elementos
        T elemTemp = nodoRaiz.getElem();
        nodoRaiz.setElem(nodoTemp.getElem());
        nodoTemp.setElem(elemTemp);
        // le asigno a la raiz el hijo izquierdo del hijo izquierdo de la raiz
        nodoRaiz.setIzq(nodoRaiz.getIzq().getIzq());
        // acomodo los enlaces al nodo temporal
        nodoTemp.setIzq(nodoTemp.getDer());
        nodoTemp.setDer(nodoRaiz.getDer());
        // cambio el hijo derecho de la raiz
        nodoRaiz.setDer(nodoTemp);

        nodoTemp.recalcularAltura();
        nodoRaiz.recalcularAltura();
    }
}
