import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;

public class Rope {
    private RopeNode raiz;

    public Rope() {
        raiz = null;
    }

    public char pesquisa(int indice) {

        if(indice < 1 || indice > calculaPesoTotal(raiz))
            throw new IndexOutOfBoundsException();

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
        return no.data.charAt(i-1);
    }

    public void concatenacao(Rope rope){
        concatenacao(rope.raiz);
    }
    public void concatenacao(RopeNode novoNo) {
        raiz = concatenacao(raiz, novoNo);
    }

    private RopeNode concatenacao(RopeNode left, RopeNode right){
        if (left != null && left.data == null && left.right == null) {
            left.right = right;
            return left;
        }

        RopeNode novo = new RopeNode();

        if(left == null) {
            novo.left = right;
            novo.right = null;
        }else{
            novo.left = left;
            novo.right = right;
        }

        novo.weight = calculaPesoTotal(novo.left);

        return novo;
    }

    //TODO: divisao não funciona
    public Rope divisao(int indice) {
        Rope novaArvore = new Rope();

        Deque<RopeNode> arvoresParaConcatenar = new ArrayDeque<>();

        RopeNode temp = raiz;

        while (true) {

            if (temp.weight < indice && temp.right != null) {
                indice -= temp.weight;
                temp = temp.right;
                continue;
            }

            if (temp.weight >= indice && temp.left != null) {
                if (temp.right != null) {
                    arvoresParaConcatenar.addFirst(temp.right);
                    temp.right = null;
                }

                if (temp.left.weight > indice && temp.left.left == null && temp.left.data != null) {
                    RopeNode novaSubArvore = new RopeNode();
                    novaSubArvore.left = new RopeNode(temp.left.data.substring(0, indice));
                    novaSubArvore.right = new RopeNode(temp.left.data.substring(indice));
                    temp.left = novaSubArvore;
                    recalcularPesos();
                } else
                    temp = temp.left;

                continue;
            }

            if(temp.weight == indice) {
                if (temp.right != null) {
                    arvoresParaConcatenar.addFirst(temp.right);
                    temp.right = null;
                }
            }
            break;
        }

        arvoresParaConcatenar.forEach((arvore) -> novaArvore.concatenacao(arvore));

        recalcularPesos();
        novaArvore.recalcularPesos();

        return novaArvore;
    }

    //TODO
    public void insere(int indice, RopeNode novo) {
        raiz = insere(indice, novo, raiz);
    }

    //TODO: falta caso insira a meio de um nó existente, veriicação indice nos limites
    private RopeNode insere(int i, RopeNode novo, RopeNode no) {

        if (no == null)
            return null;

        if (i == 1) {
            if (no.right != null)
                //novo = balancear(novo, no.right);

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

    //TODO
    public void exclusao(int inicio, int tamanho){
        Rope direita = divisao(inicio + tamanho);
        divisao(inicio);
        //raiz = balancear(raiz, direita.raiz);
    }

    //TODO: imprimir de inicio até fim
    public void report(int inicio, int fim){
    }


    public void imprimirArvore(){
        imprimirArvore(raiz, 0);
    }

    private void imprimirArvore(RopeNode no, int nivel){

        if(no == null)
            return;

        System.out.println("\t".repeat(nivel) + "Nivel: " + nivel);
        System.out.println("\t".repeat(nivel) + "Dados: '" + no.data + "' Peso: "+ no.weight);

        if(no.left != null) {
            System.out.println("\t".repeat(nivel) + "Descentes esquerdos");
            imprimirArvore(no.left, nivel + 1);
        }

        if(no.right != null) {
            System.out.println("\t".repeat(nivel) + "Descentes direitos");
            imprimirArvore(no.right, nivel + 1);
        }
    }

    private int calculaPesoTotal(RopeNode node){
        int soma = node.weight;
        RopeNode temp = node.right;
        while (temp != null){
            soma += temp.weight;
            temp = temp.right;
        }
        return soma;
    }

    public void recalcularPesos() {
        recalcularPesos(raiz);
    }
    private int recalcularPesos(RopeNode no) {

        if(no == null)
            return 0;

        if(no.data != null) {
            no.weight = no.data.length();
            return no.weight;
        }

        no.weight = recalcularPesos(no.left);


        return no.weight + recalcularPesos(no.right);
    }
}