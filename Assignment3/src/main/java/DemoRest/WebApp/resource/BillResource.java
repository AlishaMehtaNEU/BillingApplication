package DemoRest.WebApp.resource;
import DemoRest.WebApp.model.Bill;
import DemoRest.WebApp.model.Users;
import DemoRest.WebApp.repository.BillRepo;
import DemoRest.WebApp.repository.BillRepository;
import DemoRest.WebApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/v1")
public class BillResource {

//    @Autowired
//    BillRepository billRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    BillRepo billRepo;

    @RequestMapping(method = RequestMethod.POST, consumes = "application/json", value = "/bill/")
    public ResponseEntity addBill(@Valid @RequestBody final Map<String, Object> bill) throws ParseException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        try {
            Bill newBillToCreate = new Bill();
            if(!bill.containsKey("vendor") || !bill.containsKey("bill_date") || !bill.containsKey("due_date")
            || !bill.containsKey("amount_due") || !bill.containsKey("categories") || !bill.containsKey("paymentStatus")){
                return new ResponseEntity(HttpStatus.BAD_REQUEST);
            }
            for (String key : bill.keySet()) {
                if (key.equals("id") && (String) bill.get(key) != null)
                    return new ResponseEntity("Id cannot be inserted", HttpStatus.BAD_REQUEST);
                if (key.equals("created_ts") && (String) bill.get(key) != null)
                    return new ResponseEntity("created_ts cannot be inserted", HttpStatus.BAD_REQUEST);
                if (key.equals("updated_ts") && (String) bill.get(key) != null)
                    return new ResponseEntity("updated_ts cannot be inserted", HttpStatus.BAD_REQUEST);
                if (key.equals("owner_id") && (String) bill.get(key) != null)
                    return new ResponseEntity("owner_id cannot be inserted", HttpStatus.BAD_REQUEST);

                if (key.equals("vendor")) {
                    if (key.equals("vendor") && ((String) bill.get(key) == null || (String) bill.get(key) == "")) {
                        return new ResponseEntity("Vendor cannot be blank", HttpStatus.BAD_REQUEST);
                    }
                    newBillToCreate.setVendor((String) bill.get(key));
                }
                if (key.equals("bill_date")) {
                    if (key.equals("bill_date") && ((String) bill.get(key) == null || (String) bill.get(key) == "")) {
                        return new ResponseEntity("bill_date cannot be blank", HttpStatus.BAD_REQUEST);
                    }
                    SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
                    Date date1 = formatter1.parse((String) bill.get(key));
                    newBillToCreate.setBill_date(date1);
                }
                if (key.equals("due_date")) {
                    if (key.equals("due_date") && ((String) bill.get(key) == null || (String) bill.get(key) == "")) {
                        return new ResponseEntity("due_date cannot be blank", HttpStatus.BAD_REQUEST);
                    }
                    SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
                    Date date1 = formatter1.parse((String) bill.get(key));
                    newBillToCreate.setDue_date(date1);
                }
                if (key.equals("amount_due")) {
                    if (!((String) ("'" + bill.get(key) + "'")).contains(".")) {
                        return new ResponseEntity("Enter double value for amount", HttpStatus.BAD_REQUEST);
                    }
                    if ((Double) bill.get(key) < 0.01) {
                        return new ResponseEntity("amount_due should be > 0.01", HttpStatus.BAD_REQUEST);
                    }
                    newBillToCreate.setAmount_due((Double) bill.get(key));
                }
                if (key.equals("categories")) {
                    Set<String> categoryNoDup = new HashSet<>((List<String>) bill.get(key));

                    if (key.equals("categories") && categoryNoDup.size() == 0) {
                        return new ResponseEntity("categories cannot be blank", HttpStatus.BAD_REQUEST);
                    }
                    String addCategory = "";
                    for (String c : categoryNoDup) {
                        addCategory = addCategory + c + ",";
                    }
                    addCategory = addCategory.substring(0, addCategory.length() - 1);
                    newBillToCreate.setCategories(addCategory);
                }
                if (key.equals("paymentStatus")) {
                    if (key.equals("paymentStatus") && ((String) bill.get(key) == null || (String) bill.get(key) == "")) {
                        return new ResponseEntity("paymentStatus cannot be blank", HttpStatus.BAD_REQUEST);
                    }
                    boolean checkStatus = false;
                    for(Enum status : Bill.paymentStatus.values()){
                        if(status.name().equals((String) bill.get(key))){
                            checkStatus = true;
                            break;
                        }
                    }
                    if(!checkStatus)
                        return new ResponseEntity("paymentStatus should be a valid status", HttpStatus.BAD_REQUEST);
                    newBillToCreate.setPaymentStatus(DemoRest.WebApp.model.Bill.paymentStatus.valueOf((String) bill.get(key)));
                }
            }

            Users userId = userRepository.findByEmailAddress(authentication.getName());

            Date date = new Date();
            newBillToCreate.setCreated_ts(date);
            newBillToCreate.setUpdated_ts(date);
            newBillToCreate.setOwnerId(userId.getId());

            billRepo.save(newBillToCreate);

            Map<String, Object> newBill = new HashMap<>();

            newBill.put("id", newBillToCreate.getId());
            newBill.put("created_ts", newBillToCreate.getCreated_ts());
            newBill.put("updated_ts", newBillToCreate.getUpdated_ts());
            newBill.put("owner_id", newBillToCreate.getOwnerId());
            newBill.put("vendor", newBillToCreate.getVendor());
            newBill.put("bill_date", ((String) bill.get("bill_date")).substring(0, 10));
            newBill.put("due_date", ((String) bill.get("due_date")).substring(0, 10));
            newBill.put("amount_due", newBillToCreate.getAmount_due());

            List<String> allCategories = new ArrayList<>();
            if(newBillToCreate.getCategories().contains(",")) {
                for (String category : newBillToCreate.getCategories().split(",")) {
                    allCategories.add(category);
                }
            }
            else
                allCategories.add(newBillToCreate.getCategories());
            newBill.put("categories", allCategories);
            newBill.put("paymentStatus", newBillToCreate.getPaymentStatus());
            return new ResponseEntity(newBill, HttpStatus.CREATED);
        }
        catch (Exception e){
            return new ResponseEntity(e, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/bills")
    public ResponseEntity getAllBills() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Users users = userRepository.findByEmailAddress(authentication.getName());

        List<Bill> bills = billRepo.findBillsByOwnerId(users.getId());
        List<Map<String, Object>> allBills = new ArrayList<>();

        for(Bill billById : bills) {
            Map<String, Object> newBill = new HashMap<>();
            newBill.put("id", billById.getId());
            newBill.put("created_ts", billById.getCreated_ts());
            newBill.put("updated_ts", billById.getUpdated_ts());
            newBill.put("owner_id", billById.getOwnerId());
            newBill.put("vendor", billById.getVendor());
            newBill.put("bill_date", (String.valueOf(billById.getBill_date()).substring(0, 10)));
            newBill.put("due_date", (String.valueOf(billById.getDue_date()).substring(0, 10)));
            newBill.put("amount_due", billById.getAmount_due());

            List<String> allCategories = new ArrayList<>();
            if (billById.getCategories().contains(",")) {
                for (String category : billById.getCategories().split(",")) {
                    allCategories.add(category);
                }
            } else
                allCategories.add(billById.getCategories());
            newBill.put("categories", allCategories);
            newBill.put("paymentStatus", billById.getPaymentStatus());
            allBills.add(newBill);
        }
        return new ResponseEntity(allBills, HttpStatus.OK);
    }

    @GetMapping(value = "/bill/{id}")
    public ResponseEntity getBillById(HttpServletResponse response, @PathVariable String id) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            Bill billById = billRepo.findBillById(id);
            Users users = userRepository.findByEmailAddress(authentication.getName());

            if (!users.getId().equals(billById.getOwnerId())) {
                return new ResponseEntity(HttpStatus.UNAUTHORIZED);
            } else if (users.getId().equals(billById.getOwnerId())) {

                Map<String, Object> newBill = new HashMap<>();

                newBill.put("id", billById.getId());
                newBill.put("created_ts", billById.getCreated_ts());
                newBill.put("updated_ts", billById.getUpdated_ts());
                newBill.put("owner_id", billById.getOwnerId());
                newBill.put("vendor", billById.getVendor());
                newBill.put("bill_date", (String.valueOf(billById.getBill_date()).substring(0, 10)));
                newBill.put("due_date", (String.valueOf(billById.getDue_date()).substring(0, 10)));
                newBill.put("amount_due", billById.getAmount_due());

                List<String> allCategories = new ArrayList<>();
                if (billById.getCategories().contains(",")) {
                    for (String category : billById.getCategories().split(",")) {
                        allCategories.add(category);
                    }
                } else
                    allCategories.add(billById.getCategories());
                newBill.put("categories", allCategories);
                newBill.put("paymentStatus", billById.getPaymentStatus());

                return new ResponseEntity(newBill, HttpStatus.OK);
            } else
                return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        catch(Exception e ){
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/bill/{id}")
    public ResponseEntity deleteBillById(@PathVariable String id) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            Bill billById = billRepo.findBillById(id);
            Users users = userRepository.findByEmailAddress(authentication.getName());

            if (!users.getId().equals(billById.getOwnerId())) {
                return new ResponseEntity(HttpStatus.UNAUTHORIZED);
            } else if (users.getId().equals(billById.getOwnerId())) {
                billRepo.delete(billById);
                return new ResponseEntity(HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity(HttpStatus.NOT_FOUND);
            }
        }
        catch(Exception e ){
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/bill/{id}")
    public ResponseEntity updateBillById(@Valid @RequestBody final Map<String, Object> bill, @PathVariable String id) throws ParseException {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            Bill newBillToUpdate = billRepo.findBillById(id);
            Users users = userRepository.findByEmailAddress(authentication.getName());
            if(!bill.containsKey("vendor") || !bill.containsKey("bill_date") || !bill.containsKey("due_date")
                    || !bill.containsKey("amount_due") || !bill.containsKey("categories") || !bill.containsKey("paymentStatus")){
                return new ResponseEntity(HttpStatus.BAD_REQUEST);
            }

            if (!users.getId().equals(newBillToUpdate.getOwnerId())) {
                return new ResponseEntity(HttpStatus.UNAUTHORIZED);
            }
            else if(users.getId().equals(newBillToUpdate.getOwnerId())) {
                for (String key : bill.keySet()) {
                    if (key.equals("id") && (String) bill.get(key) != null)
                        return new ResponseEntity("Id cannot be updated", HttpStatus.BAD_REQUEST);
                    if (key.equals("created_ts") && (String) bill.get(key) != null)
                        return new ResponseEntity("created_ts cannot be updated", HttpStatus.BAD_REQUEST);
                    if (key.equals("updated_ts") && (String) bill.get(key) != null)
                        return new ResponseEntity("updated_ts cannot be updated", HttpStatus.BAD_REQUEST);
                    if (key.equals("owner_id") && (String) bill.get(key) != null)
                        return new ResponseEntity("owner_id cannot be updated", HttpStatus.BAD_REQUEST);

                    if (key.equals("vendor")) {
                        if (key.equals("vendor") && ((String) bill.get(key) == null || (String) bill.get(key) == "")) {
                            return new ResponseEntity("Vendor cannot be blank", HttpStatus.BAD_REQUEST);
                        }
                        newBillToUpdate.setVendor((String) bill.get(key));
                    }
                    if (key.equals("bill_date")) {
                        if (key.equals("bill_date") && ((String) bill.get(key) == null || (String) bill.get(key) == "")) {
                            return new ResponseEntity("bill_date cannot be blank", HttpStatus.BAD_REQUEST);
                        }
                        SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
                        Date date1 = formatter1.parse((String) bill.get(key));
                        newBillToUpdate.setBill_date(date1);
                    }
                    if (key.equals("due_date")) {
                        if (key.equals("due_date") && ((String) bill.get(key) == null || (String) bill.get(key) == "")) {
                            return new ResponseEntity("due_date cannot be blank", HttpStatus.BAD_REQUEST);
                        }
                        SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
                        Date date1 = formatter1.parse((String) bill.get(key));
                        newBillToUpdate.setDue_date(date1);
                    }
                    if (key.equals("amount_due")) {
                        if (!((String) ("'" + bill.get(key) + "'")).contains(".")) {
                            return new ResponseEntity("Enter double value for amount", HttpStatus.BAD_REQUEST);
                        }
                        if ((Double) bill.get(key) < 0.01) {
                            return new ResponseEntity("amount_due should be > 0.01", HttpStatus.BAD_REQUEST);
                        }
                        newBillToUpdate.setAmount_due((Double) bill.get(key));
                    }
                    if (key.equals("categories")) {
                        Set<String> categoryNoDup = new HashSet<>((List<String>) bill.get(key));

                        if (key.equals("categories") && categoryNoDup.size() == 0) {
                            return new ResponseEntity("categories cannot be blank", HttpStatus.BAD_REQUEST);
                        }
                        String addCategory = "";
                        for (String c : categoryNoDup) {
                            addCategory = addCategory + c + ",";
                        }
                        addCategory = addCategory.substring(0, addCategory.length() - 1);
                        newBillToUpdate.setCategories(addCategory);
                    }
                    if (key.equals("paymentStatus")) {
                        if (key.equals("paymentStatus") && ((String) bill.get(key) == null || (String) bill.get(key) == "")) {
                            return new ResponseEntity("paymentStatus cannot be blank", HttpStatus.BAD_REQUEST);
                        }
                        boolean checkStatus = false;
                        for(Enum status : Bill.paymentStatus.values()){
                            if(status.name().equals((String) bill.get(key))){
                                checkStatus = true;
                                break;
                            }
                        }
                        if(!checkStatus)
                            return new ResponseEntity("paymentStatus should be a valid status", HttpStatus.BAD_REQUEST);

                        newBillToUpdate.setPaymentStatus(DemoRest.WebApp.model.Bill.paymentStatus.valueOf((String) bill.get(key)));
                    }
                    newBillToUpdate.setUpdated_ts(new Date());
                }
                billRepo.save(newBillToUpdate);
                billRepo.flush();

                Map<String, Object> newBill = new HashMap<>();

                newBill.put("id", newBillToUpdate.getId());
                newBill.put("created_ts", newBillToUpdate.getCreated_ts());
                newBill.put("updated_ts", newBillToUpdate.getUpdated_ts());
                newBill.put("owner_id", newBillToUpdate.getOwnerId());
                newBill.put("vendor", newBillToUpdate.getVendor());
                newBill.put("bill_date", ((String) bill.get("bill_date")).substring(0, 10));
                newBill.put("due_date", ((String) bill.get("due_date")).substring(0, 10));
                newBill.put("amount_due", newBillToUpdate.getAmount_due());

                List<String> allCategories = new ArrayList<>();
                if(newBillToUpdate.getCategories().contains(",")) {
                    for (String category : newBillToUpdate.getCategories().split(",")) {
                        allCategories.add(category);
                    }
                }
                else
                    allCategories.add(newBillToUpdate.getCategories());
                newBill.put("categories", allCategories);
                newBill.put("paymentStatus", newBillToUpdate.getPaymentStatus());

                return new ResponseEntity(newBill, HttpStatus.OK);
            }
            else{
                return new ResponseEntity(HttpStatus.BAD_REQUEST);
            }
        }
        catch(Exception e){
            return new ResponseEntity(e, HttpStatus.NOT_FOUND);
        }
    }
}
