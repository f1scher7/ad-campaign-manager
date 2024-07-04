import {Component, OnInit} from '@angular/core';
import {AdminNavbarComponent} from "../../admin-navbar/admin-navbar.component";
import {CampaignModel} from "../../../../models/campaign.model";
import {CampaignService} from "../../../../services/campaign.service";
import {RoleEnum} from "../../../../enums/role.enum";
import {NgForOf} from "@angular/common";
import {CampaignCreateModalComponent} from "../campaign-create-modal/campaign-create-modal.component";

@Component({
  selector: 'app-campaign-management',
  standalone: true,
    imports: [
        AdminNavbarComponent,
        NgForOf,
        CampaignCreateModalComponent
    ],
  templateUrl: './campaign-management.component.html',
  styleUrl: './campaign-management.component.css'
})
export class CampaignManagementComponent implements OnInit {
    campaigns: CampaignModel[] = [];
    isModalVisible = false;
    selectedCampaign: CampaignModel = {} as CampaignModel;
    errors: any = {};

    constructor(private campaignService: CampaignService) {
    }

    ngOnInit() {
        this.loadCampaigns();
    }

    loadCampaigns() {
        this.campaignService.getAllCampaigns().subscribe(
            campaigns => {
                this.campaigns = campaigns;
            },
            error => {
                console.log('Error fetching campaigns:', error);
            }
        );
    }

    deleteCampaign(campaignToDelete: CampaignModel) {
        if (confirm('Are you sure you want to delete this campaign?')) {
            this.campaignService.deleteCampaign(campaignToDelete).subscribe(() => {
                this.campaigns = this.campaigns.filter(campaign => campaign.id !== campaignToDelete.id);
            }, error => {
                console.log('Error deleting campaign:', error);
            });
        }
    }

    showAddCampaignModal() {
        this.selectedCampaign = {
            id: 0,
            user: {id: 0, username: '', email: '', role: RoleEnum.USER, password: ''},
            campaignName: '',
            keywords: [],
            bidAmount: 0,
            campaignFund: 0,
            campaignStatus: false,
            town: '',
            radius: 0
        };
        this.isModalVisible = true;
    }

    showEditCampaignModal(campaign: CampaignModel) {
        this.selectedCampaign = { ...campaign };
        this.isModalVisible = true;
    }

    onModalClose() {
        this.errors = {};
        this.isModalVisible = false;
    }

    onSaveCampaign({ campaign, username, keywords }: { campaign: CampaignModel, username: string, keywords: string }) {
        if (campaign.id != 0) {
            this.campaignService.updateCampaign({ campaign, keywords }).subscribe(updatedCampaign => {
                this.loadCampaigns();
                this.isModalVisible = false;
                this.errors = {};
            }, error => {
                this.handleErrors(error);
            });
        } else {
            this.campaignService.createCampaign({ campaign, username, keywords }).subscribe(newCampaign => {
                this.campaigns.push(newCampaign);
                this.isModalVisible = false;
                this.loadCampaigns();
                this.errors = {};
            }, error => {
                this.handleErrors(error);
            });
        }
    }

    handleErrors(error: any) {
        if (error.error && error.error.usernameError) {
            this.errors.usernameError = error.error.usernameError;
        }
        if (error.error && error.error.campaignNameError) {
            this.errors.campaignNameError = error.error.campaignNameError;
        }
        console.log('Error:', error);
    }
}
