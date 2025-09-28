package com.project.fgh.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.project.fgh.services.DiasHabitoService;

import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
@RequestMapping("/diashabito")
public class DiasHabitoController {

	private final DiasHabitoService diasHabitoService;
}
