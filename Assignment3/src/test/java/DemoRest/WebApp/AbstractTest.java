package DemoRest.WebApp;

import java.beans.PropertyEditor;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.*;

import DemoRest.WebApp.model.Bill;
import DemoRest.WebApp.model.Users;
import DemoRest.WebApp.repository.BillRepo;
import DemoRest.WebApp.repository.BillRepository;
import DemoRest.WebApp.repository.UserRepository;
import DemoRest.WebApp.resource.UserResource;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
public class AbstractTest {

    @Autowired
    private UserResource userResource;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    public BillRepo billRepo;

    @Autowired
    private Users users;

    @Autowired
    private Bill bill;

//    @Test
//    public void testFindAll() throws Exception {
//        // given
//        Users u1 = new Users();
//        u1.setPassword("Aa3###12");
//        u1.setFirst_name("Alisha");
//        u1.setLast_name("Mehta");
//        u1.setEmailAddress("mehta.al@husly.neu.edu");
//
//        List<Users> users = new ArrayList<Users>();
//        users.add(u1);
//        UserRepository userRepository = new UserRepository() {
//            @Override
//            public Users findByEmailAddress(String email_address) {
//                return null;
//            }
//
//            @Override
//            public List<Users> findAll() {
//                return null;
//            }
//
//            @Override
//            public List<Users> findAll(Sort sort) {
//                return null;
//            }
//
//            @Override
//            public List<Users> findAllById(Iterable<Integer> iterable) {
//                return null;
//            }
//
//            @Override
//            public <S extends Users> List<S> saveAll(Iterable<S> iterable) {
//                return null;
//            }
//
//            @Override
//            public void flush() {
//
//            }
//
//            @Override
//            public <S extends Users> S saveAndFlush(S s) {
//                return null;
//            }
//
//            @Override
//            public void deleteInBatch(Iterable<Users> iterable) {
//
//            }
//
//            @Override
//            public void deleteAllInBatch() {
//
//            }
//
//            @Override
//            public Users getOne(Integer integer) {
//                return null;
//            }
//
//            @Override
//            public <S extends Users> List<S> findAll(Example<S> example) {
//                return null;
//            }
//
//            @Override
//            public <S extends Users> List<S> findAll(Example<S> example, Sort sort) {
//                return null;
//            }
//
//            @Override
//            public Page<Users> findAll(Pageable pageable) {
//                return null;
//            }
//
//            @Override
//            public <S extends Users> S save(S s) {
//                return null;
//            }
//
//            @Override
//            public Optional<Users> findById(Integer integer) {
//                return Optional.empty();
//            }
//
//            @Override
//            public boolean existsById(Integer integer) {
//                return false;
//            }
//
//            @Override
//            public long count() {
//                return 0;
//            }
//
//            @Override
//            public void deleteById(Integer integer) {
//
//            }
//
//            @Override
//            public void delete(Users users) {
//
//            }
//
//            @Override
//            public void deleteAll(Iterable<? extends Users> iterable) {
//
//            }
//
//            @Override
//            public void deleteAll() {
//
//            }
//
//            @Override
//            public <S extends Users> Optional<S> findOne(Example<S> example) {
//                return Optional.empty();
//            }
//
//            @Override
//            public <S extends Users> Page<S> findAll(Example<S> example, Pageable pageable) {
//                return null;
//            }
//
//            @Override
//            public <S extends Users> long count(Example<S> example) {
//                return 0;
//            }
//
//            @Override
//            public <S extends Users> boolean exists(Example<S> example) {
//                return false;
//            }
//
//            @Override
//            public Users findById(String id) {
//                return null;
//            }
//
//        };
//        userRepository.save(u1);
//        Users ufound = userRepository.findByEmailAddress(u1.getEmailAddress());
//
////        assertThat(u1.getFirst_name())
////                .isEqualTo(resp.get);
//
//    }

    @Test
    public void testForBills() throws Exception {
        SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        Date date1 = formatter1.parse("2020-01-12");

        Users u1 = new Users();
        u1.setPassword("Aa3###12");
        u1.setFirst_name("Alisha");
        u1.setLast_name("Mehta");
        u1.setEmailAddress("mehta.al@husly.neu.edu");
        UserRepository userRepository = new UserRepository() {
            @Override
            public Users findByEmailAddress(String email_address) {
                return null;
            }

            @Override
            public List<Users> findAll() {
                return null;
            }

            @Override
            public List<Users> findAll(Sort sort) {
                return null;
            }

            @Override
            public List<Users> findAllById(Iterable<Integer> iterable) {
                return null;
            }

            @Override
            public <S extends Users> List<S> saveAll(Iterable<S> iterable) {
                return null;
            }

            @Override
            public void flush() {

            }

            @Override
            public <S extends Users> S saveAndFlush(S s) {
                return null;
            }

            @Override
            public void deleteInBatch(Iterable<Users> iterable) {

            }

            @Override
            public void deleteAllInBatch() {

            }

            @Override
            public Users getOne(Integer integer) {
                return null;
            }

            @Override
            public <S extends Users> List<S> findAll(Example<S> example) {
                return null;
            }

            @Override
            public <S extends Users> List<S> findAll(Example<S> example, Sort sort) {
                return null;
            }

            @Override
            public Page<Users> findAll(Pageable pageable) {
                return null;
            }

            @Override
            public <S extends Users> S save(S s) {
                return null;
            }

            @Override
            public Optional<Users> findById(Integer integer) {
                return Optional.empty();
            }

            @Override
            public boolean existsById(Integer integer) {
                return false;
            }

            @Override
            public long count() {
                return 0;
            }

            @Override
            public void deleteById(Integer integer) {

            }

            @Override
            public void delete(Users users) {

            }

            @Override
            public void deleteAll(Iterable<? extends Users> iterable) {

            }

            @Override
            public void deleteAll() {

            }

            @Override
            public <S extends Users> Optional<S> findOne(Example<S> example) {
                return Optional.empty();
            }

            @Override
            public <S extends Users> Page<S> findAll(Example<S> example, Pageable pageable) {
                return null;
            }

            @Override
            public <S extends Users> long count(Example<S> example) {
                return 0;
            }

            @Override
            public <S extends Users> boolean exists(Example<S> example) {
                return false;
            }

            @Override
            public Users findById(String id) {
                return null;
            }

        };
        userRepository.save(u1);
        userRepository.flush();

        Bill bill = new Bill();
        bill.setVendor("NEU");
        bill.setBill_date(date1);
        bill.setDue_date(date1);
        bill.setAmount_due(7000.01);
        bill.setCategories("Spring");
        bill.setPaymentStatus(DemoRest.WebApp.model.Bill.paymentStatus.valueOf("due"));
        bill.setOwnerId(u1.getId());
        bill.setCreated_ts(date1);
        bill.setUpdated_ts(date1);

        BillRepository billRepository = new BillRepository() {
            @Override
            public Bill findBillById(String Id) {
                return null;
            }

            @Override
            public List<Bill> findBillsByOwnerId(String owner_id) {
                return null;
            }

            @Override
            public List<Bill> findAll() {
                return null;
            }

            @Override
            public List<Bill> findAll(Sort sort) {
                return null;
            }

            @Override
            public List<Bill> findAllById(Iterable<Integer> iterable) {
                return null;
            }

            @Override
            public <S extends Bill> List<S> saveAll(Iterable<S> iterable) {
                return null;
            }

            @Override
            public void flush() {

            }

            @Override
            public <S extends Bill> S saveAndFlush(S s) {
                return null;
            }

            @Override
            public void deleteInBatch(Iterable<Bill> iterable) {

            }

            @Override
            public void deleteAllInBatch() {

            }

            @Override
            public Bill getOne(Integer integer) {
                return null;
            }

            @Override
            public <S extends Bill> List<S> findAll(Example<S> example) {
                return null;
            }

            @Override
            public <S extends Bill> List<S> findAll(Example<S> example, Sort sort) {
                return null;
            }

            @Override
            public Page<Bill> findAll(Pageable pageable) {
                return null;
            }

            @Override
            public <S extends Bill> S save(S s) {
                return null;
            }

            @Override
            public Optional<Bill> findById(Integer integer) {
                return Optional.empty();
            }

            @Override
            public boolean existsById(Integer integer) {
                return false;
            }

            @Override
            public long count() {
                return 0;
            }

            @Override
            public void deleteById(Integer integer) {

            }

            @Override
            public void delete(Bill bill) {

            }

            @Override
            public void deleteAll(Iterable<? extends Bill> iterable) {

            }

            @Override
            public void deleteAll() {

            }

            @Override
            public <S extends Bill> Optional<S> findOne(Example<S> example) {
                return Optional.empty();
            }

            @Override
            public <S extends Bill> Page<S> findAll(Example<S> example, Pageable pageable) {
                return null;
            }

            @Override
            public <S extends Bill> long count(Example<S> example) {
                return 0;
            }

            @Override
            public <S extends Bill> boolean exists(Example<S> example) {
                return false;
            }
        };

        billRepository.save(bill);
        billRepository.flush();
        billRepository.findBillById(bill.getId());
        billRepository.findBillsByOwnerId(u1.getId());
    }
}