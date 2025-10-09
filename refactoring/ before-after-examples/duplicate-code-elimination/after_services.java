// Adicionado em HabitoService.java
public List<HabitoDTO> listarTodosComoDTO() {
    return listarTodos().stream()
            .map(HabitoDTO::new)
            .collect(Collectors.toList());
}


// Adicionado em DiasHabitoService.java
public List<DiasHabitoDTO> listarTodosComoDTO() {
    return listarTodos().stream()
            .map(DiasHabitoDTO::new)
            .collect(Collectors.toList());
}