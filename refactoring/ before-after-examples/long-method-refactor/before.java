// Em FrequenciaService.java

public int calcularDiasEsperados(Habito habito, LocalDate inicio, LocalDate fim) {
    TipoFrequencia tipo = habito.getTipoFrequencia();
    String config = habito.getConfigFrequencia();
    int diasEsperados = 0;

    LocalDate dataInicioCalculo = inicio.isBefore(habito.getDataCriacao()) ? habito.getDataCriacao() : inicio;

    if (dataInicioCalculo.isAfter(fim)) {
        return 0;
    }

    switch (tipo) {
        case DIARIAMENTE:
            diasEsperados = (int) ChronoUnit.DAYS.between(dataInicioCalculo, fim) + 1;
            break;

        case DIAS_ESPECIFICOS:
            Set<DayOfWeek> diasPermitidos = Arrays.stream(config.split(","))
                    .map(String::trim)
                    .map(String::toUpperCase)
                    .map(this::mapearDiaSemana)
                    .collect(Collectors.toSet());

            for (LocalDate data = dataInicioCalculo; !data.isAfter(fim); data = data.plusDays(1)) {
                if (diasPermitidos.contains(data.getDayOfWeek())) {
                    diasEsperados++;
                }
            }
            break;

        case INTERVALO_DE_DIAS:
            try {
                int intervalo = Integer.parseInt(config);
                if (intervalo <= 0) break;

                LocalDate dataHabito = habito.getDataCriacao();
                while (dataHabito.isBefore(dataInicioCalculo)) {
                    dataHabito = dataHabito.plusDays(intervalo);
                }
                while (!dataHabito.isAfter(fim)) {
                    diasEsperados++;
                    dataHabito = dataHabito.plusDays(intervalo);
                }
            } catch (NumberFormatException e) {
                diasEsperados = 0;
            }
            break;

        case VEZES_POR_SEMANA:
            try {
                int vezesPorSemana = Integer.parseInt(config);
                long totalDiasNoPeriodo = ChronoUnit.DAYS.between(dataInicioCalculo, fim) + 1;
                diasEsperados = (int) Math.round((totalDiasNoPeriodo / 7.0) * vezesPorSemana);
            } catch (NumberFormatException e) {
                diasEsperados = 0;
            }
            break;

        default:
            diasEsperados = 0;
            break;
    }

    return diasEsperados;
}