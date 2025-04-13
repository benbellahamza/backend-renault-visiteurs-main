package org.sid.renaultvisiteursbackend.Controller;

import lombok.RequiredArgsConstructor;
import org.sid.renaultvisiteursbackend.Dto.ResponsableDTO;
import org.sid.renaultvisiteursbackend.Service.ResponsableService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/gestion-responsables")
@CrossOrigin("*")
@RequiredArgsConstructor
public class ResponsableCompteController {

    private final ResponsableService service;

    @PostMapping
    public ResponsableDTO createResponsable(@RequestBody ResponsableDTO dto) {
        return service.createResponsable(dto);
    }

    @GetMapping
    public List<ResponsableDTO> getAllResponsables() {
        return service.getAllResponsables();
    }
}
