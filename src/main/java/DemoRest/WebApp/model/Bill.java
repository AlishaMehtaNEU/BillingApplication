package DemoRest.WebApp.model;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Component
@Entity
@Table(name = "bill")
public class Bill {

    public enum paymentStatus {
        paid, due, past_due, no_payment_required;
    }

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "id")
    private String id;

    @Column(name = "created_ts")
    private Date created_ts;

    @Column(name = "updated_ts")
    private Date updated_ts;

    @NotNull(message = "Owner Id is compulsory")
    @Column(name = "owner_id")
    private String ownerId;

    @NotNull(message = "vendor is compulsory")
    @Column(name = "vendor")
    private String vendor;

    @NotNull(message = "Bill date Id is compulsory")
    @Column(name = "bill_date")
    private Date bill_date;

    @NotNull(message = "Due Date is compulsory")
    @Column(name = "due_date")
    private Date due_date;

    @NotNull(message = "Amount due is compulsory")
    @Column(name = "amount_due")
    private Double amount_due;

    @NotNull(message = "categories is compulsory")
    @Column(name = "categories")
    private String categories;

    @NotNull(message = "paymentStatus is compulsory")
    @Enumerated(EnumType.STRING)
    @Column(name = "paymentStatus")
    private paymentStatus paymentStatus;

    public String getAttachment() {
        return attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }

    @Column(name = "attachment")
    private String attachment;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getCreated_ts() {
        return created_ts;
    }

    public void setCreated_ts(Date created_ts) {
        this.created_ts = created_ts;
    }

    public Date getUpdated_ts() {
        return updated_ts;
    }

    public void setUpdated_ts(Date updated_ts) {
        this.updated_ts = updated_ts;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public Date getBill_date() {
        return bill_date;
    }

    public void setBill_date(Date bill_date) {
        this.bill_date = bill_date;
    }

    public Date getDue_date() {
        return due_date;
    }

    public void setDue_date(Date due_date) {
        this.due_date = due_date;
    }

    public Double getAmount_due() {
        return amount_due;
    }

    public void setAmount_due(Double amount_due) {
        this.amount_due = amount_due;
    }

    public String getCategories() {
        return categories;
    }

    public void setCategories(String categories) {
        this.categories = categories;
    }

    public paymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(paymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }
}
