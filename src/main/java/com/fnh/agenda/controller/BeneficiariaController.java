package com.fnh.agenda.controller;

import com.fnh.agenda.dto.BeneficiariaDTO;
import com.fnh.agenda.model.Beneficiaria;
import com.fnh.agenda.service.BeneficiariaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/beneficiarias")
public class BeneficiariaController {

    @Autowired
    private BeneficiariaService beneficiariaService;

    /** 
     * Listar todas las beneficiarias con paginación y filtros 
     */
    @GetMapping
    public String listarBeneficiarias(Model model,
                                      @RequestParam(defaultValue = "0") int page,
                                      @RequestParam(defaultValue = "10") int size,
                                      @RequestParam(required = false) String filtroNombre,
                                      @RequestParam(required = false) String filtroCarpeta) {
        
        Pageable pageable = PageRequest.of(page, size);
        Page<Beneficiaria> beneficiarias = beneficiariaService.findAll(pageable, filtroNombre, filtroCarpeta);

        model.addAttribute("beneficiarias", beneficiarias.getContent());
        model.addAttribute("totalPages", beneficiarias.getTotalPages());
        model.addAttribute("currentPage", page);
        return "beneficiarias/lista";
    }

    /** 
     * Mostrar formulario para agregar una nueva beneficiaria 
     */
    @GetMapping("/nueva")
    public String mostrarFormularioRegistro(Model model) {
        model.addAttribute("beneficiariaDTO", new BeneficiariaDTO());
        return "beneficiarias/formulario";
    }

    /** 
     * Guardar una nueva beneficiaria 
     */
    @PostMapping
    public String guardarBeneficiaria(@ModelAttribute BeneficiariaDTO beneficiariaDTO) {
        beneficiariaService.create(beneficiariaDTO);
        return "redirect:/beneficiarias";
    }

    /** 
     * Mostrar formulario para editar una beneficiaria 
     */
    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Long id, Model model) {
        Optional<Beneficiaria> beneficiariaOpt = beneficiariaService.findById(id);
        if (beneficiariaOpt.isPresent()) {
            BeneficiariaDTO beneficiariaDTO = new BeneficiariaDTO();
            Beneficiaria beneficiaria = beneficiariaOpt.get();
            beneficiariaDTO.setNombres(beneficiaria.getNombres());
            beneficiariaDTO.setApellidos(beneficiaria.getApellidos());
            beneficiariaDTO.setTelefono(beneficiaria.getTelefono());
            beneficiariaDTO.setDireccion(beneficiaria.getDireccion());
            beneficiariaDTO.setEdad(Integer.parseInt(beneficiaria.getEdad()));
            beneficiariaDTO.setCodigoCarpeta(beneficiaria.getCodigoCarpeta());

            model.addAttribute("beneficiariaDTO", beneficiariaDTO);
            model.addAttribute("id", id);
            return "beneficiarias/formulario";
        }
        return "redirect:/beneficiarias";
    }

    /** 
     * Actualizar beneficiaria 
     */
    @PostMapping("/actualizar/{id}")
    public String actualizarBeneficiaria(@PathVariable Long id, @ModelAttribute BeneficiariaDTO beneficiariaDTO) {
        beneficiariaService.update(id, beneficiariaDTO);
        return "redirect:/beneficiarias";
    }

    /** 
     * Eliminar una beneficiaria 
     */
    @GetMapping("/eliminar/{id}")
    public String eliminarBeneficiaria(@PathVariable Long id) {
        beneficiariaService.delete(id);
        return "redirect:/beneficiarias";
    }
}
