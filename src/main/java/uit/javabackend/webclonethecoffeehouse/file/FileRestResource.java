package uit.javabackend.webclonethecoffeehouse.file;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uit.javabackend.webclonethecoffeehouse.security.authorization.TCHOperation;

@RestController
@RequestMapping("api/Files")
@CrossOrigin(origins = "*")
public class FileRestResource {
    private final FileService fileService;

    public FileRestResource(FileService fileService) {
        this.fileService = fileService;
    }

    @TCHOperation(name = "UploadFiles")
    @PostMapping(path = "/Upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadFiles(@RequestPart("file") MultipartFile file) {
        fileService.init();
        fileService.save(file);
        return new ResponseEntity<>("", HttpStatus.OK);
    }

    @TCHOperation(name = "LoadFile")
    @GetMapping("/{filename}") // đọc file
    public ResponseEntity<?> loadFile(@PathVariable("filename") String fileName) {
        Resource resource = fileService.load(fileName);
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(resource);
    }
}
