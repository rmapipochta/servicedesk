package com.itsmtools.common.dictionary.model;


import org.hibernate.annotations.*;
import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "ticket")
public class Ticket {

    @Id
    @Column
    @GeneratedValue
    private Integer id;

    @Column
    @Type(type = "text")
    private String title;

    @Column
    private Long dateOpen;

    @Column
    private Long dateClose;

    @Column
    @Type(type = "text")
    private String description;

    @Column
    private Integer progress;

    @ManyToOne
    @JoinColumn(
        name = "contractor_id",
        referencedColumnName = "id"
    )
    private Contractor contractor;

    @ManyToOne
    @JoinColumn(
        name = "initiator_id",
        referencedColumnName = "id"
    )
    private Account initiator;

    @ManyToOne
    @JoinColumn(
        name = "author_id",
        referencedColumnName = "id"
    )
    private Account author;

    @ManyToOne
    @JoinColumn(
        name = "performer_id",
        referencedColumnName = "id"
    )
    private Account performer;

    @ManyToOne
    @JoinColumn(
        name = "ticket_type_id",
        referencedColumnName = "id"
    )
    private TicketType ticketType;

    @ManyToOne
    @JoinColumn(
        name = "it_service_id",
        referencedColumnName = "id"
    )
    private Service service;

    @ManyToOne
    @JoinColumn(
        name = "priority_id",
        referencedColumnName = "id"
    )
    private TicketPriority priority;

    @ManyToOne
    @JoinColumn(
        name = "support_level_id",
        referencedColumnName = "id"
    )
    private SupportLevel supportLevel;

    @OneToMany(
        fetch = FetchType.EAGER,
        cascade = CascadeType.ALL,
        mappedBy = "ticket",
        orphanRemoval=true
    )
    @Fetch(FetchMode.SUBSELECT)
    private Set<TicketAttache> attaches;

    // getters and setters

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getDateOpen() {
        return dateOpen;
    }

    public void setDateOpen(Long dateOpen) {
        this.dateOpen = dateOpen;
    }

    public Long getDateClose() {
        return dateClose;
    }

    public void setDateClose(Long dateClose) {
        this.dateClose = dateClose;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getProgress() {
        return progress;
    }

    public void setProgress(Integer progress) {
        this.progress = progress;
    }

    public Account getInitiator() {
        return initiator;
    }

    public void setInitiator(Account initiator) {
        this.initiator = initiator;
    }

    public Account getAuthor() {
        return author;
    }

    public void setAuthor(Account author) {
        this.author = author;
    }

    public Account getPerformer() {
        return performer;
    }

    public void setPerformer(Account performer) {
        this.performer = performer;
    }

    public TicketType getTicketType() {
        return ticketType;
    }

    public void setTicketType(TicketType ticketType) {
        this.ticketType = ticketType;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public TicketPriority getPriority() {
        return priority;
    }

    public void setPriority(TicketPriority priority) {
        this.priority = priority;
    }

    public SupportLevel getSupportLevel() {
        return supportLevel;
    }

    public void setSupportLevel(SupportLevel supportLevel) {
        this.supportLevel = supportLevel;
    }

    public Contractor getContractor() {
        return contractor;
    }

    public void setContractor(Contractor contractor) {
        this.contractor = contractor;
    }

    public Set<TicketAttache> getAttaches() {
        return attaches;
    }

    public void setAttaches(Set<TicketAttache> attaches) {
        if(this.attaches == null) {
            this.attaches = attaches != null ? attaches : new HashSet<>();
        } else {
            this.attaches.clear();
            this.attaches.addAll(attaches);
        }
        this.attaches.forEach(e -> e.setTicket(this));
    }
}