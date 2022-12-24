public class Main {
    public static void main(String[] args) {

        //exemploConcatenacao();
        exemploPesquisa();

    }



    public static void exemploPesquisa(){
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

        arvoreFinal.imprimirArvore();
        System.out.println("Caracter com indide é o: " + arvoreFinal.pesquisa(10));
    }

    public static void exemploConcatenacao(){
        Rope arvore1 = new Rope();
        Rope arvore2 = new Rope();

        arvore1.concatenacao(new RopeNode("Hello_"));
        arvore1.concatenacao(new RopeNode("my_"));
        arvore2.concatenacao(new RopeNode("na"));
        arvore2.concatenacao(new RopeNode("me_i"));

        arvore1.concatenacao(arvore2);

        arvore1.imprimirArvore();
    }

}