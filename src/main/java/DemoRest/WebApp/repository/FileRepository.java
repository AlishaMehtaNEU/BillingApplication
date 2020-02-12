package DemoRest.WebApp.repository;

import DemoRest.WebApp.model.File;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<File, Integer> {

    public File findFileById(String Id);
    public File findFileByBill(String Id);
}
