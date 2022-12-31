import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;

public class Rope {
    private RopeNode raiz;

    public Rope() {
        raiz = null;
    }

    public char pesquisa(int indice) {

        if (indice < 1 || indice > calculaPesoTotal(raiz))
            throw new IndexOutOfBoundsException();

        return pesquisa(indice, raiz);
    }

    private char pesquisa(int i, RopeNode no) {
        if (no.weight < i) {
            i -= no.weight;

            if (no.right != null)
                return pesquisa(i, no.right);
        }

        if (no.weight > i || (no.weight == i && i > 1)) {
            if (no.left != null)
                return pesquisa(i, no.left);
        }

        return no.data.charAt(i - 1);
    }

    public void concatenacao(Rope rope) {
        concatenacao(rope.raiz);
    }

    public void concatenacao(RopeNode novoNo) {
        raiz = concatenacao(raiz, novoNo);
    }

    private RopeNode concatenacao(RopeNode left, RopeNode right) {

        if (left != null && left.data == null && left.right == null) {
            left.right = right;
            return left;
        }

        RopeNode novo = new RopeNode();

        if (left == null) {
            novo.left = right;
            novo.right = null;
        } else {
            novo.left = left;
            novo.right = right;
        }

        novo.weight = calculaPesoTotal(novo.left);

        return novo;
    }

    public Rope divisao(int indice) {

        if (indice < 1 || indice > calculaPesoTotal(raiz))
            throw new IndexOutOfBoundsException();

        return divisao(indice, raiz);
    }

    private Rope divisao(int indice, RopeNode raiz) {
        Rope novaArvore = new Rope();

        Deque<RopeNode> arvoresParaConcatenar = new ArrayDeque<>();

        RopeNode temp = raiz;

        while (true) {

            if (temp.right != null) {

                if (temp.weight < indice && temp.right.right == null && temp.right.data != null && indice - temp.weight != calculaPesoTotal(temp.right)) {
                    RopeNode novaSubArvore = new RopeNode();
                    novaSubArvore.left = new RopeNode(temp.right.data.substring(0, indice - 1));
                    novaSubArvore.right = new RopeNode(temp.right.data.substring(indice - 1));
                    temp.right = novaSubArvore;
                    recalcularPesos();
                }

                if (temp.weight < indice) {
                    indice -= temp.weight;
                    temp = temp.right;
                    continue;
                }
            }

            if (temp.left != null) {

                if (temp.left.weight > indice && temp.left.left == null && temp.left.data != null) {
                    RopeNode novaSubArvore = new RopeNode();
                    novaSubArvore.left = new RopeNode(temp.left.data.substring(0, indice));
                    novaSubArvore.right = new RopeNode(temp.left.data.substring(indice));
                    temp.left = novaSubArvore;
                    recalcularPesos();
                }

                if (temp.weight >= indice) {
                    if (temp.right != null) {
                        arvoresParaConcatenar.addFirst(temp.right);
                        temp.right = null;
                    }
                    temp = temp.left;
                }
                continue;
            }

            if (temp.weight == indice) {
                if (temp.right != null) {
                    arvoresParaConcatenar.addFirst(temp.right);
                    temp.right = null;
                }
            }
            break;
        }

        if (arvoresParaConcatenar.size() > 1)
            arvoresParaConcatenar.forEach((arvore) -> novaArvore.concatenacao(arvore));
        else
            novaArvore.raiz = arvoresParaConcatenar.getFirst();

        recalcularPesos();
        novaArvore.recalcularPesos();

        return novaArvore;
    }

    public void insere(int indice, String nova) {

        if (indice < 1 || indice > calculaPesoTotal(raiz))
            throw new IndexOutOfBoundsException();

        insere(indice, new RopeNode(nova));
    }

    private void insere(int i, RopeNode nova) {

        Rope right = divisao(i);
        right.raiz = concatenacao(nova, right.raiz);

        RopeNode temp = raiz;
        RopeNode pontoInsercao = null;

        while (temp != null) {
            if (temp.right == null && temp.data == null) {
                pontoInsercao = temp;
                temp = temp.left;
            } else
                temp = temp.right;
        }

        if (pontoInsercao == null)
            concatenacao(right);
        else
            pontoInsercao.right = right.raiz;

        recalcularPesos();
    }

    public void exclusao(int inicio, int tamanho) {

        if (inicio < 1 || inicio + tamanho > calculaPesoTotal(raiz))
            throw new IndexOutOfBoundsException();

        Rope direita = divisao(inicio + tamanho);
        divisao(inicio);
        raiz = concatenacao(raiz, direita.raiz);
    }

    public String report(int inicio, int tamanho) {

        if (inicio < 1 || inicio + tamanho > calculaPesoTotal(raiz))
            throw new IndexOutOfBoundsException();

        return report(inicio, inicio + tamanho + 1, raiz);
    }

    private String report(int inicio, int fim, RopeNode raiz) {

        StringBuilder sb = new StringBuilder();
        RopeNode noAtual = raiz;
        int temp = inicio;

        while (inicio < fim) {
            if (noAtual.weight < temp && noAtual.data == null) {
                temp -= noAtual.weight;

                if (noAtual.right != null)
                    noAtual = noAtual.right;
                continue;
            }

            if (noAtual.weight >= temp && noAtual.data == null) {
                if (noAtual.left != null)
                    noAtual = noAtual.left;
                continue;
            }

            if (noAtual.data.length() + inicio > fim) {
                sb.append(noAtual.data, temp - 1, fim - inicio);
                break;
            } else
                sb.append(noAtual.data, temp - 1, noAtual.data.length());

            inicio += noAtual.data.length() - temp + 1;
            noAtual = raiz;
            temp = inicio;
        }
        return sb.toString();
    }

    public void rebalance() {
        ArrayList<RopeNode> folhas;
        ArrayList<RopeNode> res = new ArrayList<>();
        rebalance(res, raiz);

        do {

            folhas = new ArrayList<>(res);
            res.clear();

            if (folhas.size() % 2 == 1)
                folhas.add(null);

            while (folhas.size() > 1) {
                res.add(concatenacao(folhas.remove(0), folhas.remove(0)));
            }

        } while (res.size() > 1);

        raiz = res.get(0);
    }

    private void rebalance(ArrayList<RopeNode> folhas, RopeNode no) {

        if (no.left != null)
            rebalance(folhas, no.left);

        if (no.right != null)
            rebalance(folhas, no.right);

        if (no.data != null && !no.data.isEmpty())
            folhas.add(no);

    }

    public void imprimirArvore() {
        imprimirArvore(raiz, 0);
    }

    private void imprimirArvore(RopeNode no, int nivel) {

        if (no == null)
            return;

        System.out.println("\t".repeat(nivel) + "Nivel: " + nivel);
        System.out.println("\t".repeat(nivel) + "Dados: '" + no.data + "' Peso: " + no.weight);

        if (no.left != null) {
            System.out.println("\t".repeat(nivel) + "Descentes esquerdos");
            imprimirArvore(no.left, nivel + 1);
        }

        if (no.right != null) {
            System.out.println("\t".repeat(nivel) + "Descentes direitos");
            imprimirArvore(no.right, nivel + 1);
        }
    }

    private int calculaPesoTotal(RopeNode node) {
        int soma = node.weight;
        RopeNode temp = node.right;
        while (temp != null) {
            soma += temp.weight;
            temp = temp.right;
        }
        return soma;
    }

    public void recalcularPesos() {
        recalcularPesos(raiz);
    }

    private int recalcularPesos(RopeNode no) {

        if (no == null)
            return 0;

        if (no.data != null) {
            no.weight = no.data.length();
            return no.weight;
        }

        no.weight = recalcularPesos(no.left);


        return no.weight + recalcularPesos(no.right);
    }
}