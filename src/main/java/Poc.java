import utils.PropertiesReader;
import utils.WebDriverManager;

import static pages.Login.loginPonto;
import static pages.Menu.abrirDigitacaoDeMarcacao;
import static pages.Portal.apontamentoPresente;
import static pages.Portal.preencherDigitacaoDeMarcacao;
import static pages.Portal.salvarLastWorkedDate;
import static pages.Portal.salvarMarcacao;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import domain.Jornada;

public class Poc {
    static WebDriverManager driver;

    public static void main(String[] args) throws Exception {
        try {

            Jornada jornada;
            LocalDateTime agora = LocalDateTime.now();

            if (agora.toLocalDate().getDayOfWeek().getValue() == 5 // Sexta
             && agora.toLocalTime().isAfter(LocalTime.of(16, 0)))
                jornada = new Jornada(agora.toLocalTime());
            else if (PropertiesReader.getPontoProperties("lastWorked.date").equals(LocalDate.now().toString()))
                throw new RuntimeException("a data " + LocalDate.now() + " ja foi lancada");
            else
                jornada = new Jornada();

            loginPonto();

//        LocalDate inicioMes = LocalDate.parse( "2021-01-01" );
//        abrirDigitacaoDeMarcacao();
//        while( !inicioMes.isAfter( LocalDate.now() ) ) {
//            preencherDigitacaoDeMarcacao( new Jornada( inicioMes ) );
//            salvarMarcacao();
//            inicioMes = inicioMes.plusDays( 1 );
//        }

            if (!apontamentoPresente(jornada)) {
                abrirDigitacaoDeMarcacao();
                preencherDigitacaoDeMarcacao(jornada);
                salvarMarcacao();
            }

            if (apontamentoPresente(jornada))
                salvarLastWorkedDate();

            driver = WebDriverManager.getInstance();
            driver.quit();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.print("aperte enter no console para sair");
            System.in.read();
            if (driver == null)
                driver = WebDriverManager.getInstance();
            if (driver != null && driver.getWebDriver() != null)
                driver.quit();
        }
    }

}
