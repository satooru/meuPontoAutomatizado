package step;

import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.E;
import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.Quando;

public class pontoStep {

    @Dado("que eu realize login no portal de ponto")
    public void queEuRealizeLoginNoPortalDePonto() {
        System.out.println("que eu realize login no portal de ponto");
    }

    @E("que o ponto não esteja preenchido")
    public void queOPontoNaoEstejaPreenchido() {
        System.out.println("que o ponto não esteja preenchido");
    }

    @Quando("preencher o ponto")
    public void preencherOPonto() {
        System.out.println("preencher o ponto");
    }

    @Então("o apontamento deverá ser contabilizado")
    public void oApontamentoDeveraSerContabilizado() {
        System.out.println("o apontamento deverá ser contabilizado");
    }
}
