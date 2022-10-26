package uit.javabackend.webclonethecoffeehouse.file;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uit.javabackend.webclonethecoffeehouse.common.util.ResponseUtil;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


public interface FileService {
    public void init();
    public void save(MultipartFile file);
    public Resource load(String fileName);
}

@Service
class FileServiceImp implements FileService{
    private final Path root = Paths.get("images");
    @Override
    public void init() {
        try{
            if(!Files.exists(root)){
                // Tạo folder root
                Files.createDirectory(root);
            }
        }catch (Exception e) {
           System.out.println("Error create root folder" +e.getMessage()); // chuyển về logger sau
        }
    }

    @Override
    public void save(MultipartFile file) {
        try{
           Files.copy(file.getInputStream(),this.root.resolve(file.getOriginalFilename()));
        }catch (Exception e) {
            System.out.println("Error save file to root folder" +e.getMessage()); // chuyển về logger sau
        }
    }

    @Override
    public Resource load(String fileName) {
        try{
            Path file =this.root.resolve(fileName);
            Resource resource = new UrlResource(file.toUri());
            if(resource.exists() || resource.isReadable()){
                return resource;
            }else{
                throw new RuntimeException("Error resource not exits");
            }
        }catch (Exception e) {
            throw new RuntimeException("Error resource not exits" +e.getMessage());
        }


    }
}
