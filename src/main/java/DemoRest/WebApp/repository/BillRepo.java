package DemoRest.WebApp.repository;

import DemoRest.WebApp.model.Bill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BillRepo {
    @Autowired
    BillRepository billRepository;

    public Bill findBillById(String id) {
        Bill bill = billRepository.findBillById(id);
        return bill;
    }
    public List<Bill> findBillsByOwnerId(String owner_id) {
        List<Bill> bills = billRepository.findBillsByOwnerId(owner_id);
        return bills;
    }
    public Bill save(Bill bill){
        System.out.println("bill --------- " + bill);
        Bill billSaved = billRepository.save(bill);
        return billSaved;
    }
    public void delete(Bill bill){
        billRepository.delete(bill);
    }
    public void flush(){
        billRepository.flush();
    }
}
