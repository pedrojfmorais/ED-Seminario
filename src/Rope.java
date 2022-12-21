/**
 * Class Rope
 **/
public class Rope {
    private RopeNode raiz;

    public Rope() {
        raiz = new RopeNode("");
    }

    public void limparRope() {
        raiz = new RopeNode("");
    }

    public char pesquisa(int indice) {
        return pesquisa(indice, raiz);
    }

    private char pesquisa(int i, RopeNode no) {
        if (no.weight < i) {
            i -= no.weight;

            if (no.right != null)
                return pesquisa(i, no.right);
        }

        if (no.weight > i) {
            if (no.left != null)
                return pesquisa(i, no.left);
        }
        return no.data.charAt(i);
    }

    public void concatenacao(RopeNode novoNo) {
        RopeNode novaRaiz = new RopeNode();
        novaRaiz.left = raiz;
        novaRaiz.right = novoNo;
        novaRaiz.weight = 0;

        RopeNode calculoPeso = novaRaiz.left;

        while (calculoPeso != null) {
            novaRaiz.weight += calculoPeso.weight;
            calculoPeso = calculoPeso.right;
        }

        raiz = novaRaiz;
    }

    public RopeNode balancear(RopeNode left, RopeNode right) {

        if (left.data == null && left.right == null) {
            left.right = right;
            return left;
        }
        if (right.data == null && right.left == null) {
            right.left = left;
            return right;
        }

        RopeNode novo = new RopeNode();
        novo.left = left;
        novo.right = right;

        return novo;
    }

    //TODO: divisao não funciona
    public RopeNode divisao(int indice) {
        RopeNode novaArvore = null;

        RopeNode temp = raiz;

        while (true) {

            if (temp.weight < indice && temp.right != null) {
                indice -= temp.weight;
                temp = temp.right;
            }

            if (temp.weight > indice && temp.left != null) {

                if (novaArvore == null)
                    novaArvore = temp.right;

                novaArvore = balancear(novaArvore, temp.right);

                temp = temp.left;
            }

        }
    }

    public void insere(int indice, RopeNode novo) {
        raiz = insere(indice, novo, raiz);
    }

    //TODO: falta caso insira a meio de um nó existente, veriicação indice nos limites
    private RopeNode insere(int i, RopeNode novo, RopeNode no) {

        if (no == null)
            return null;

        if (i == 1) {
            if (no.right != null)
                novo = balancear(novo, no.right);

            no.right = novo;
            return no;
        }

        if (no.weight < i) {
            i -= no.weight;

            if (no.right != null)
                no.right = insere(i, novo, no.right);
        }

        if (no.weight > i) {
            if (no.left != null)
                no.left = insere(i, novo, no.left);
        }
        return no;
    }

    public void exclusao(int inicio, int tamanho){
        RopeNode direita = divisao(inicio + tamanho);
        divisao(inicio);
        raiz = balancear(raiz, direita);
    }

    //TODO: imprimir de inicio até fim
    public void report(int inicio, int fim){
    }
}