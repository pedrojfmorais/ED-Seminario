public class Main {
    public static void main(String[] args) {

//        exemploPesquisa();
//        exemploConcatenacao();
//        exemploDivisaoUltimoCaracter();
//        exemploDivisaoCaracterMeio();
//        exemploInserir();
        exemploExclusao();
//        exemploReport();
//        exemploRebalance();
    }

    public static void exemploPesquisa() {

        Rope arvore = constroiArvoreHello_my_name_is_Simon();

        System.out.println("Árvore original:");
        arvore.imprimirArvore();

        System.out.println("\nResultado:");
        System.out.println("Caracter no indide 10 é o: " + arvore.pesquisa(10));
    }

    public static void exemploConcatenacao() {
        Rope arvore1 = new Rope();
        Rope arvore2 = new Rope();

        arvore1.concatenacao(new RopeNode("Hello_"));
        arvore1.concatenacao(new RopeNode("my_"));

        arvore2.concatenacao(new RopeNode("na"));
        arvore2.concatenacao(new RopeNode("me_i"));

        System.out.println("Árvores antes de concatenar:");
        arvore1.imprimirArvore();
        arvore2.imprimirArvore();

        arvore1.concatenacao(arvore2);

        System.out.println("\nÁrvores após concatenar:");
        arvore1.imprimirArvore();
    }

    public static void exemploDivisaoUltimoCaracter() {

        Rope arvore = constroiArvoreHello_my_name_is_Simon();

        System.out.println("Árvore original:");
        arvore.imprimirArvore();

        Rope arvoreDivisao = arvore.divisao(11); // subarvore esquerda
//        Rope arvoreDivisao = arvore.divisao(15); // subarvore direita

        System.out.println("\nÁrvores resultantes:");
        arvore.imprimirArvore();
        arvoreDivisao.imprimirArvore();
    }

    public static void exemploDivisaoCaracterMeio() {
        Rope arvoreFinal = new Rope();
        Rope arvore2 = new Rope();

        arvoreFinal.concatenacao(new RopeNode("Spli"));
        arvoreFinal.concatenacao(new RopeNode("tting"));

        arvore2.concatenacao(new RopeNode("_or_r"));
        arvore2.concatenacao(new RopeNode("opes"));

        arvoreFinal.concatenacao(arvore2);

        System.out.println("Árvore original:");
        arvoreFinal.imprimirArvore();

        Rope arvoreDivisao = arvoreFinal.divisao(12); // subarvore esquerda
//        Rope arvoreDivisao = arvoreFinal.divisao(6); // subarvore direita

        System.out.println("\nÁrvores resultantes:");
        arvoreFinal.imprimirArvore();
        arvoreDivisao.imprimirArvore();
    }

    public static void exemploInserir() {

        Rope arvore = constroiArvoreHello_my_name_is_Simon();

        System.out.println("Árvore original:");
        arvore.imprimirArvore();

        arvore.insere(16, "_Cowell"); // subarvore esquerda fim
//        arvore.insere(10, "_Cowell"); // subarvore esquerda meio
//        arvore.insere(15, "_Cowell"); // subarvore direita fim
//        arvore.insere(17, "_Cowell"); // subarvore direita meio

        System.out.println("\nÁrvore após inserção:");
        arvore.imprimirArvore();
    }

    public static void exemploExclusao() {
        Rope arvore = constroiArvoreHello_my_name_is_Simon();

        System.out.println("Árvore original:");
        arvore.imprimirArvore();

        arvore.exclusao(9, 6); // fim nós
//        arvore.exclusao(3, 7); // meio nós

        System.out.println("\nÁrvore após exclusão:");
        arvore.imprimirArvore();
    }

    public static void exemploReport() {

        Rope arvore = constroiArvoreHello_my_name_is_Simon();
        arvore.imprimirArvore();

        String str = arvore.report(8, 14);
//        String str = arvore.report(1, 22);
        System.out.println(System.lineSeparator() + "String final: " + str);
    }

    public static void exemploRebalance() {

        Rope arvore = constroiArvoreUnbalanced();
        arvore.imprimirArvore();

        arvore.rebalance();

        arvore.imprimirArvore();
    }

    private static Rope constroiArvoreHello_my_name_is_Simon(){
        Rope arvore1 = new Rope();
        Rope arvore2 = new Rope();
        Rope arvore3 = new Rope();
        Rope arvoreFinal = new Rope();

        arvore1.concatenacao(new RopeNode("Hello_"));
        arvore1.concatenacao(new RopeNode("my_"));

        arvore2.concatenacao(new RopeNode("na"));
        arvore2.concatenacao(new RopeNode("me_i"));

        arvore3.concatenacao(new RopeNode("s"));
        arvore3.concatenacao(new RopeNode("_Simon"));

        arvore2.concatenacao(arvore3);
        arvore1.concatenacao(arvore2);

        arvoreFinal.concatenacao(arvore1);

        return arvoreFinal;
    }

    private static Rope constroiArvoreUnbalanced(){
        Rope arvore1 = new Rope();
        Rope arvore2 = new Rope();
        Rope arvore3 = new Rope();
        Rope arvoreFinal = new Rope();

        arvore1.concatenacao(new RopeNode("Hello_"));
        arvore1.concatenacao(new RopeNode(""));

        RopeNode emptyNode = new RopeNode();
        emptyNode.right = new RopeNode("na");

        arvore2.concatenacao(emptyNode);
        arvore2.concatenacao(new RopeNode("me_i"));

        arvore3.concatenacao(new RopeNode("s"));
        arvore3.concatenacao(new RopeNode("_Simon"));

        arvore2.concatenacao(arvore3);
        arvore1.concatenacao(arvore2);

        arvoreFinal.concatenacao(arvore1);

        return arvoreFinal;
    }
}