package com.cbpaguay.myexcel.srv;

import com.cbpaguay.myexcel.dao.AutoDao;
import com.cbpaguay.myexcel.dto.Auto;
import com.github.liaochong.myexcel.core.DefaultExcelBuilder;
import com.github.liaochong.myexcel.core.SaxExcelReader;
import com.github.liaochong.myexcel.utils.AttachmentExportUtil;

import lombok.AllArgsConstructor;
import lombok.extern.apachecommons.CommonsLog;

import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

@Service
@AllArgsConstructor
@CommonsLog
public class AutoSrv {

    private final AutoDao autoDao;

    public List<Auto> findAll() {
        return autoDao.findAll();
    }

    public Auto save(Auto item) {
        return autoDao.save(item);
    }

    public void deleteAll() {
        autoDao.deleteAll();
    }

    public ResponseEntity<?> importar(MultipartFile file) {
        try {
            var filecito = File.createTempFile("autitos", file.getOriginalFilename());
            file.transferTo(filecito);
            var autos = SaxExcelReader.of(Auto.class)
                    .sheet(0)
                    .rowFilter(r -> r.getRowNum() > 0)
                    .beanFilter(b -> !ObjectUtils.isEmpty(b.getMarca()))
                    .read(filecito);
            autoDao.saveAll(autos);

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .build();

        } catch (Exception ex) {
            log.error(" ERROR AL IMPORTAR :: " + ex);
            return ResponseEntity
                    .status(HttpStatus.NOT_IMPLEMENTED)
                    .build();

        }
    }

    public void exportar(HttpServletResponse response) {
        var autos = findAll();
        Workbook wb = DefaultExcelBuilder.of(Auto.class)
                .sheetName("Autos")
                .titles(Arrays.asList("Index", "Marca", "Modelo"))
                .noStyle()
                .build(autos);
        AttachmentExportUtil.export(wb, "autos.xlsx", response);
    }
}
