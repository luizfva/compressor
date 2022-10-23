package uk.co.correvate.compressor.integration.restapi;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * {@link CompressController}
 *
 * @author Luiz Azevedo
 */
@RestController
public class CompressController {

    @PostMapping(value = "/compress", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void compress(@RequestParam("files") final List<MultipartFile> uploadedFiles, final HttpServletResponse response) throws IOException {

        try (
                final ServletOutputStream outputStream = response.getOutputStream();
                final ZipOutputStream zipOutputStream = new ZipOutputStream(outputStream)
        ) {
            for (final MultipartFile uploadedFile : uploadedFiles) {

                final InputStream is = uploadedFile.getInputStream();

                final String filename = StringUtils.hasText(uploadedFile.getOriginalFilename()) ? uploadedFile.getOriginalFilename() : uploadedFile.getName();
                final ZipEntry zipEntry = new ZipEntry(filename);

                zipOutputStream.putNextEntry(zipEntry);
                IOUtils.copy(is, zipOutputStream);

                is.close();
            }

            response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"download.zip\"");
        }
    }
}