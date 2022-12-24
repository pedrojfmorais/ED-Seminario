public class Rope {
    private RopeNode raiz;

    public Rope() {
        raiz = null;
    }

    public void limparRope() {
        raiz = new RopeNode("");
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
        raiz = balancear(raiz, novoNo);
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

    public RopeNode balancear(RopeNode left, RopeNode right) {

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
}