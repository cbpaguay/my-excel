package com.cbpaguay.myexcel.ctrl;

import com.cbpaguay.myexcel.dto.Auto;
import com.cbpaguay.myexcel.srv.AutoSrv;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

@RestController
@AllArgsConstructor
public class AutoCtrl {

    private final AutoSrv autoSrv;

    @GetMapping("/api/autos")
    public List<Auto> findAll() {
        return autoSrv.findAll();
    }

    @DeleteMapping("/api/autos")
    public void deleteAll() {
        autoSrv.deleteAll();
    }

    @PostMapping("/api/auto")
    public Auto save(@RequestBody Auto item) {
        return autoSrv.save(item);
    }

    @PostMapping("/api/importar")
    public ResponseEntity<?> importar(@RequestPart("file") MultipartFile file) {
        return autoSrv.importar(file);
    }

    @GetMapping("/api/exportar")
    public void exportar(HttpServletResponse response) {
        autoSrv.exportar(response);
    }


}
