package domain;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.Random;

public class Jornada {

    LocalDate dia;
    LocalTime entrada;
    LocalTime inicioAlmoco;
    LocalTime fimAlmoco;
    LocalTime saida;

    private static DateTimeFormatter localDateformat = DateTimeFormatter.ofPattern("dd/MM/uuuu");

    public Jornada() {
        this(null, null, null, null);
    }

    public Jornada(LocalDate dia) {
        this(null, null, null, dia);
    }

    public Jornada(LocalTime saida) {
        this(null, null, saida, LocalDate.now());
    }

    public Jornada(LocalTime entrada, LocalTime inicioAlmoco, LocalTime saida, LocalDate dia) {
        Random random = new Random();
        this.entrada = Optional.ofNullable(entrada)
                               .orElse(Optional.ofNullable(saida).map(s -> s.minusHours(9))
                                               .orElse(LocalTime.parse("09:01").plusMinutes(random.nextInt(10))));
        this.inicioAlmoco = Optional.ofNullable(inicioAlmoco)
                                    .orElse(LocalTime.parse("12:00").plusMinutes(random.nextInt(91)));
        this.fimAlmoco = this.inicioAlmoco.plusHours(1);
        this.saida = this.entrada.plusHours(9);
        this.dia = Optional.ofNullable(dia)
                           .orElse( LocalDate.now());
    }

    public String getEntrada() {
        return entrada.toString();
    }

    public String getSaida() {
        return saida.toString();
    }

    public String getInicioAlmoco() {
        return inicioAlmoco.toString();
    }

    public String getFimAlmoco() {
        return fimAlmoco.toString();
    }

    public String getDia() {
        return dia.format(localDateformat);
    }

    public LocalDate getDiaLocalDate() {
        return dia;
    }
}
