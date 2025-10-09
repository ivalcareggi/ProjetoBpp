// Em FrequenciaService.java

public int calcularDiasEsperados(Habito habito, LocalDate inicio, LocalDate fim) {
    LocalDate dataInicioCalculo = inicio.isBefore(habito.getDataCriacao()) ? habito.getDataCriacao() : inicio;
    if (dataInicioCalculo.isAfter(fim)) {
        return 0;
    }

    switch (habito.getTipoFrequencia()) {
        case DIARIAMENTE:
            return calcularDiariamente(dataInicioCalculo, fim);
        case DIAS_ESPECIFICOS:
            return calcularDiasEspecificos(habito.getConfigFrequencia(), dataInicioCalculo, fim);
        case INTERVALO_DE_DIAS:
            return calcularIntervaloDeDias(habito, dataInicioCalculo, fim);
        case VEZES_POR_SEMANA:
            return calcularVezesPorSemana(habito.getConfigFrequencia(), dataInicioCalculo, fim);
        default:
            return 0;
    }
}

private int calcularDiariamente(LocalDate inicio, LocalDate fim) {
    return (int) ChronoUnit.DAYS.between(inicio, fim) + 1;
}

private int calcularDiasEspecificos(String config, LocalDate inicio, LocalDate fim) {
    Set<DayOfWeek> diasPermitidos = Arrays.stream(config.split(","))
            .map(String::trim).map(String::toUpperCase).map(this::mapearDiaSemana)
            .collect(Collectors.toSet());

    int diasEsperados = 0;
    for (LocalDate data = inicio; !data.isAfter(fim); data = data.plusDays(1)) {
        if (diasPermitidos.contains(data.getDayOfWeek())) {
            diasEsperados++;
        }
    }
    return diasEsperados;
}

private int calcularIntervaloDeDias(Habito habito, LocalDate inicio, LocalDate fim) {
    try {
        int intervalo = Integer.parseInt(habito.getConfigFrequencia());
        if (intervalo <= 0) return 0;

        int diasEsperados = 0;
        LocalDate dataHabito = habito.getDataCriacao();
        while (dataHabito.isBefore(inicio)) {
            dataHabito = dataHabito.plusDays(intervalo);
        }
        while (!dataHabito.isAfter(fim)) {
            diasEsperados++;
            dataHabito = dataHabito.plusDays(intervalo);
        }
        return diasEsperados;
    } catch (NumberFormatException e) {
        return 0;
    }
}

private int calcularVezesPorSemana(String config, LocalDate inicio, LocalDate fim) {
    try {
        int vezesPorSemana = Integer.parseInt(config);
        long totalDiasNoPeriodo = ChronoUnit.DAYS.between(inicio, fim) + 1;
        return (int) Math.round((totalDiasNoPeriodo / 7.0) * vezesPorSemana);
    } catch (NumberFormatException e) {
        return 0;
    }
}