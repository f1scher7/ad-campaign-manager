package com.futurum.adcampaignmanager.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "campaigns")
public class Campaign {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @Column(unique = true)
    private String campaignName;
    @ElementCollection
    private List<String> keywords;
    private BigDecimal bidAmount;
    private BigDecimal campaignFund;
    private boolean campaignStatus;
    private String town;
    private int radius;


    public Campaign() {}

    public Campaign(User user, String campaignName, List<String> keywords, BigDecimal bidAmount, BigDecimal campaignFund, boolean campaignStatus, String town, int radius) {
        this.user = user;
        this.campaignName = campaignName;
        this.keywords = keywords;
        this.bidAmount = bidAmount;
        this.campaignFund = campaignFund;
        this.campaignStatus = campaignStatus;
        this.town = town;
        this.radius = radius;
    }


    public Long getId() {
        return this.id;
    }

    public User getUser() {
        return this.user;
    }

    public String getCampaignName() {
        return this.campaignName;
    }

    public List<String> getKeywords() {
        return this.keywords;
    }

    public BigDecimal getBidAmount() {
        return this.bidAmount;
    }

    public BigDecimal getCampaignFund() {
        return this.campaignFund;
    }

    public boolean isCampaignStatus() {
        return this.campaignStatus;
    }

    public String getTown() {
        return this.town;
    }

    public int getRadius() {
        return this.radius;
    }


    public void setUser(User user) {
        this.user = user;
    }

    public void setCampaignName(String campaignName) {
        this.campaignName = campaignName;
    }

    public void setKeywords(List<String> keywords) {
        this.keywords = keywords;
    }

    public void setBidAmount(BigDecimal bidAmount) {
        this.bidAmount = bidAmount;
    }

    public void setCampaignFund(BigDecimal campaignFund) {
        this.campaignFund = campaignFund;
    }

    public void setCampaignStatus(boolean campaignStatus) {
        this.campaignStatus = campaignStatus;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }


    @Override
    public String toString() {
        return "Campaign{" +
                "id=" + id +
                ", user=" + user +
                ", campaignName='" + campaignName + '\'' +
                ", keywords=" + keywords +
                ", bidAmount=" + bidAmount +
                ", campaignFund=" + campaignFund +
                ", campaignStatus=" + campaignStatus +
                ", town='" + town + '\'' +
                ", radius=" + radius +
                '}';
    }

}
