package DemoRest.WebApp.model;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Component
@Entity
@Table(name = "file")

public class File {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "id")
    private String id;

    @Column(name = "upload_date")
    private Date upload_date;

    @NotNull(message = "file_name is compulsory")
    @Column(name = "file_name")
    private String file_name;

    public String getFile_name_dir() {
        return file_name_dir;
    }

    public void setFile_name_dir(String file_name_dir) {
        this.file_name_dir = file_name_dir;
    }

    @Column(name = "file_name_dir")
    private String file_name_dir;

    @NotNull(message = "url is compulsory")
    @Column(name = "url")
    private String url;

    @Column(name = "mimetype")
    private String mimetype;

    @Column(name = "bill")
    private String bill;

    @Column(name = "size")
    private String size;

    @Column(name = "file_owner")
    private String file_owner;

    @Column(name = "hash")
    private String hash;

    @Column(name = "created_date")
    private Date created_date;

    public Date getCreated_date() {
        return created_date;
    }

    public void setCreated_date(Date created_date) {
        this.created_date = created_date;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getMimetype() {
        return mimetype;
    }

    public void setMimetype(String mimetype) {
        this.mimetype = mimetype;
    }

    public String getBill() {
        return bill;
    }

    public void setBill(String bill) {
        this.bill = bill;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getFile_owner() {
        return file_owner;
    }

    public void setFile_owner(String file_owner) {
        this.file_owner = file_owner;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setUpload_date(Date upload_date) {
        this.upload_date = upload_date;
    }

    public void setFile_name(String file_name) {
        this.file_name = file_name;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getId() {
        return id;
    }

    public Date getUpload_date() {
        return upload_date;
    }

    public String getFile_name() {
        return file_name;
    }

    public String getUrl() {
        return url;
    }
}