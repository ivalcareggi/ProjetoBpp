// Em HabitoController.java
@GetMapping
public ResponseEntity<List<HabitoDTO>> listarTodos() {
    List<Habito> habitos = habitoService.listarTodos();
    List<HabitoDTO> habitosDto = habitos.stream()
        .map(HabitoDTO::new)
        .collect(Collectors.toList());
    return ResponseEntity.ok(habitosDto);
}


// Em DiasHabitoController.java
@GetMapping
public ResponseEntity<List<DiasHabitoDTO>> listarTodos() {
    List<DiasHabito> habitos = diasHabitoService.listarTodos();
    List<DiasHabitoDTO> habitosDto = habitos.stream()
        .map(DiasHabitoDTO::new)
        .collect(Collectors.toList());
    return ResponseEntity.ok(habitosDto);
}