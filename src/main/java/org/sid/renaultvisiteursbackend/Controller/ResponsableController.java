package org.sid.renaultvisiteursbackend.Controller;

import org.sid.renaultvisiteursbackend.Export.ExcelExporter;
import org.sid.renaultvisiteursbackend.Entity.Visiteur;
import org.sid.renaultvisiteursbackend.Repository.VisiteurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/responsables")
@CrossOrigin("*")
public class ResponsableController {

    @Autowired
    private VisiteurRepository visiteurRepository;

    // ✅ 1. Liste paginée avec tri
    @GetMapping("/visiteurs")
    public Page<Visiteur> getVisiteursAvecPagination(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "dateEntree") String sortBy,
            @RequestParam(defaultValue = "desc") String direction
    ) {
        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();
        return visiteurRepository.findAll(PageRequest.of(page, size, sort));
    }

    // ✅ 2. Export Excel (TOUS les visiteurs)
    @GetMapping("/export/excel")
    public ResponseEntity<byte[]> exportExcel() throws IOException {
        List<Visiteur> visiteurs = visiteurRepository.findAll();
        ExcelExporter exporter = new ExcelExporter(visiteurs);
        byte[] excelData = exporter.export();

        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=visiteurs.xlsx");
        headers.setContentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));

        return ResponseEntity.ok()
                .headers(headers)
                .body(excelData);
    }
}
