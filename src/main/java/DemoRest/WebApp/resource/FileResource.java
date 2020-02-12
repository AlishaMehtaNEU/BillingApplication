package DemoRest.WebApp.resource;

import DemoRest.WebApp.model.Bill;
import DemoRest.WebApp.model.File;
import DemoRest.WebApp.model.Users;
import DemoRest.WebApp.repository.BillRepo;
import DemoRest.WebApp.repository.FileRepository;
import DemoRest.WebApp.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.DatatypeConverter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.util.*;

@RestController
@RequestMapping("/v1/bill")
public class FileResource {

    private static final String UPLOAD_FOLDER = System.getProperty("user.dir")+"/src/main/resources/static/images/";

    @Autowired
    BillRepo billRepo;

    @Autowired
    UserRepository userRepository;

    @Autowired
    FileRepository fileRepository;

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE, value = "/{id}/file")
    public ResponseEntity addAttachment(HttpServletRequest request, @PathVariable String id, @RequestParam("attachment") MultipartFile attachment) throws ParseException, IOException {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            Bill billById = billRepo.findBillById(id);
            Users users = userRepository.findByEmailAddress(authentication.getName());
            if (billById == null) {
                return new ResponseEntity(HttpStatus.NOT_FOUND);
            }
            else if (!users.getId().equals(billById.getOwnerId())) {
                return new ResponseEntity(HttpStatus.UNAUTHORIZED);
            }
            if(!attachment.getContentType().equals("image/jpg") && !attachment.getContentType().equals("image/jpeg")
            && !attachment.getContentType().equals("application/pdf") && !attachment.getContentType().equals("image/png")){
                return new ResponseEntity(HttpStatus.BAD_REQUEST);
            }
            File fileExists = fileRepository.findFileByBill(billById.getId());
            if(fileExists != null){
//                fileRepository.delete(fileExists);
//                String filePath = UPLOAD_FOLDER + fileExists.getFile_name_dir();
//                Path  fileToDelete = Paths.get(filePath);
//                Files.delete(fileToDelete);
                return new ResponseEntity("File already exists", HttpStatus.BAD_REQUEST);
            }

            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(attachment.getBytes());
            byte[] digest = md.digest();
            String myHash = DatatypeConverter
                    .printHexBinary(digest).toUpperCase();

            File file = new File();
            file.setFile_name(attachment.getOriginalFilename());
            file.setUpload_date(new Date());
            file.setUrl(UPLOAD_FOLDER);
            file.setBill(id);
            file.setFile_owner(users.getId());
            file.setMimetype(attachment.getContentType());
            file.setSize(String.valueOf(attachment.getSize()));
            file.setHash(myHash);
            file.setCreated_date(new Date());

            if (!attachment.isEmpty()) {
                byte[] bytes = attachment.getBytes();
                double x = Math.random();
                Path path = Paths.get(UPLOAD_FOLDER + x + attachment.getOriginalFilename());
                Files.write(path, bytes);
                file.setFile_name_dir(x + attachment.getOriginalFilename());
            }

            fileRepository.save(file);
            fileRepository.flush();
            ObjectMapper mapperObj = new ObjectMapper();
            Map<String, Object> displayFile = new HashMap<>();

            displayFile.put("file_name", file.getFile_name());
            displayFile.put("id", file.getId());
            displayFile.put("url", file.getUrl());
            displayFile.put("upload_date", file.getUpload_date());

            mapperObj.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS , false);
            String jsonResp = mapperObj.writeValueAsString(displayFile);
            billById.setAttachment(jsonResp);
            billRepo.save(billById);
            return new ResponseEntity(displayFile, HttpStatus.CREATED);
        }
        catch(Exception e){
            System.out.println(e);
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/{billId}/file/{fileId}")
    public ResponseEntity getCorrespondingBill(@PathVariable String billId , @PathVariable String fileId) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Users users = userRepository.findByEmailAddress(authentication.getName());
        Bill billById = billRepo.findBillById(billId);
        File file = fileRepository.findFileById(fileId);

        if(billById == null){
            return new ResponseEntity( HttpStatus.NOT_FOUND);
        }
        else if (!users.getId().equals(billById.getOwnerId())) {
            return new ResponseEntity( HttpStatus.UNAUTHORIZED);
        }
        else if(file == null){
            return new ResponseEntity( HttpStatus.NOT_FOUND);
        }
        else if(!file.getBill().equals(billId)){
            return new ResponseEntity( HttpStatus.BAD_REQUEST);
        }

        if(file.getBill().equals(billId)){
            Map<String, Object> displayFile = new HashMap<>();

            displayFile.put("file_name", file.getFile_name());
            displayFile.put("id", file.getId());
            displayFile.put("url", file.getUrl());
            displayFile.put("upload_date", file.getUpload_date());

            return new ResponseEntity( displayFile , HttpStatus.OK);
        }
        else
            return new ResponseEntity( HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{billId}/file/{fileId}")
    public ResponseEntity deleteFileById(@PathVariable String billId, @PathVariable String fileId) throws IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Users users = userRepository.findByEmailAddress(authentication.getName());
        Bill billById = billRepo.findBillById(billId);
        File file = fileRepository.findFileById(fileId);

        if(billById == null){
            return new ResponseEntity( HttpStatus.NOT_FOUND);
        }
        else if (!users.getId().equals(billById.getOwnerId())) {
            return new ResponseEntity( HttpStatus.UNAUTHORIZED);
        }
        else if(file == null){
            return new ResponseEntity( HttpStatus.NOT_FOUND);
        }
        else if(!file.getBill().equals(billId)){
            return new ResponseEntity( HttpStatus.BAD_REQUEST);
        }

        if(file.getBill().equals(billId)){
            fileRepository.delete(file);
            String filePath = UPLOAD_FOLDER + file.getFile_name_dir();
            Path  fileToDelete = Paths.get(filePath);
            Files.delete(fileToDelete);

            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        else
            return new ResponseEntity( HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(method = RequestMethod.PUT, consumes = MediaType.MULTIPART_FORM_DATA_VALUE, value = "/{id}/file")
    public ResponseEntity updateAttachment(HttpServletRequest request, @PathVariable String id, @RequestParam("attachment") MultipartFile attachment) throws ParseException, IOException {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            Bill billById = billRepo.findBillById(id);
            Users users = userRepository.findByEmailAddress(authentication.getName());
            if (billById == null) {
                return new ResponseEntity(HttpStatus.NOT_FOUND);
            }
            else if (!users.getId().equals(billById.getOwnerId())) {
                return new ResponseEntity(HttpStatus.UNAUTHORIZED);
            }
            if(!attachment.getContentType().equals("image/jpg") && !attachment.getContentType().equals("image/jpeg")
                    && !attachment.getContentType().equals("application/pdf") && !attachment.getContentType().equals("image/png")){
                return new ResponseEntity(HttpStatus.BAD_REQUEST);
            }
            File fileExists = fileRepository.findFileByBill(billById.getId());
            if(fileExists != null){
                String filePath = UPLOAD_FOLDER + fileExists.getFile_name_dir();
                Path  fileToDelete = Paths.get(filePath);
                Files.delete(fileToDelete);
            }

            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(attachment.getBytes());
            byte[] digest = md.digest();
            String myHash = DatatypeConverter
                    .printHexBinary(digest).toUpperCase();

            fileExists.setFile_name(attachment.getOriginalFilename());
            fileExists.setUpload_date(new Date());
            fileExists.setUrl(UPLOAD_FOLDER);
            fileExists.setMimetype(attachment.getContentType());
            fileExists.setSize(String.valueOf(attachment.getSize()));
            fileExists.setHash(myHash);

            if (!attachment.isEmpty()) {
                byte[] bytes = attachment.getBytes();
                double x = Math.random();
                Path path = Paths.get(UPLOAD_FOLDER + x + attachment.getOriginalFilename());
                Files.write(path, bytes);
                fileExists.setFile_name_dir(x + attachment.getOriginalFilename());
            }

            fileRepository.save(fileExists);
            fileRepository.flush();
            ObjectMapper mapperObj = new ObjectMapper();
            Map<String, Object> displayFile = new HashMap<>();

            displayFile.put("file_name", fileExists.getFile_name());
            displayFile.put("id", fileExists.getId());
            displayFile.put("url", fileExists.getUrl());
            displayFile.put("upload_date", fileExists.getUpload_date());

            mapperObj.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS , false);
            String jsonResp = mapperObj.writeValueAsString(displayFile);
            billById.setAttachment(jsonResp);
            billRepo.save(billById);
            return new ResponseEntity(displayFile, HttpStatus.CREATED);
        }
        catch(Exception e){
            System.out.println(e);
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }
}
