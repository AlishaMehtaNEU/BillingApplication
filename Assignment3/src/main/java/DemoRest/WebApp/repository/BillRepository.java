package DemoRest.WebApp.repository;

import DemoRest.WebApp.model.Bill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BillRepository extends JpaRepository<Bill, Integer> {

    public Bill findBillById(String Id);
    public List<Bill> findBillsByOwnerId(String owner_id);
}
