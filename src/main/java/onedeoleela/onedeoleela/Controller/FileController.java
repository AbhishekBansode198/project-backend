package onedeoleela.onedeoleela.Controller;




import onedeoleela.onedeoleela.Entity.FileEntity;
import onedeoleela.onedeoleela.Service.FileService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/files")
public class FileController {

    private final FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    // ----------------- Upload a file -----------------
//    @PostMapping("/upload")
//    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file,
//                                        @RequestParam("username") String username) {
//        try {
//            FileEntity savedFile = fileService.uploadFile(file, username);
//            return ResponseEntity.ok("File uploaded successfully: " + savedFile.getFileName() +
//                    " (" + savedFile.getFileType() + ")");
//        } catch (Exception e) {
//            return ResponseEntity.status(500).body("File upload failed: " + e.getMessage());
//        }
//    }
    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(
            @RequestParam("file") MultipartFile file,
            @RequestParam("eCode") Long eCode) {

        try {
            FileEntity savedFile = fileService.uploadFile(file, eCode);
            return ResponseEntity.ok("File uploaded successfully: " + savedFile.getFileName());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("File upload failed: " + e.getMessage());
        }
    }

    // ----------------- Download a file by ID -----------------
//    @GetMapping("/download/{id}")
//    public ResponseEntity<byte[]> downloadFile(@PathVariable Long id) {
//        return fileService.downloadFile(id)
//                .map(file -> ResponseEntity.ok()
//                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFileName() + "\"")
//                        .contentType(MediaType.parseMediaType(file.getFileType()))
//                        .body(file.getFileData()))
//                .orElse(ResponseEntity.notFound().build());
//    }

    @GetMapping("/download/{id}")
    public ResponseEntity<byte[]> downloadFile(@PathVariable Long id) {

        FileEntity file = fileService.downloadFile(id)
                .orElseThrow(() -> new RuntimeException("File not found"));

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "inline; filename=\"" + file.getFileName() + "\"")
                .contentType(MediaType.parseMediaType(file.getFileType()))
                .body(file.getFileData());
    }

    // ----------------- List all files metadata -----------------
    @GetMapping("/all")
    public ResponseEntity<List<FileEntity>> getAllFiles() {
        List<FileEntity> files = fileService.getAllFiles();
        return ResponseEntity.ok(files);
    }

    // ----------------- Delete a file by ID -----------------
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteFile(@PathVariable Long id) {
        if (fileService.downloadFile(id).isPresent()) {
            fileService.deleteFile(id);
            return ResponseEntity.ok("File deleted successfully");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // ----------------- List files by uploader -----------------
    @GetMapping("/uploader/{username}")
    public ResponseEntity<List<FileEntity>> getFilesByUploader(@PathVariable String username) {
        List<FileEntity> files = fileService.getFilesByUploader(username);
        return ResponseEntity.ok(files);
    }
}