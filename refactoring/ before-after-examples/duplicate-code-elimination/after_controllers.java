// Em HabitoController.java (refatorado)
@GetMapping
public ResponseEntity<List<HabitoDTO>> listarTodos() {
    List<HabitoDTO> habitosDto = habitoService.listarTodosComoDTO();
    return ResponseEntity.ok(habitosDto);
}


// Em DiasHabitoController.java (refatorado)
@GetMapping
public ResponseEntity<List<DiasHabitoDTO>> listarTodos() {
    List<DiasHabitoDTO> habitosDto = diasHabitoService.listarTodosComoDTO();
    return ResponseEntity.ok(habitosDto);
}