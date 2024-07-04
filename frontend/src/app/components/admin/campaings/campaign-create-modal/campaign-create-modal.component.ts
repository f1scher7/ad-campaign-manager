import {Component, EventEmitter, Input, Output} from '@angular/core';
import {CampaignModel} from "../../../../models/campaign.model";
import {FormsModule} from "@angular/forms";
import {NgIf} from "@angular/common";

@Component({
  selector: 'app-campaign-create-modal',
  standalone: true,
    imports: [
        FormsModule,
        NgIf
    ],
  templateUrl: './campaign-create-modal.component.html',
  styleUrl: './campaign-create-modal.component.css'
})
export class CampaignCreateModalComponent {
    @Input() isVisible = false;
    @Input() campaign: CampaignModel = {} as CampaignModel;
    @Input() errors: any = {};
    @Output() closeModal = new EventEmitter<void>();
    @Output() saveCampaign = new EventEmitter<{ campaign: CampaignModel, username: string, keywords: string }>();

    keywordsInput: string = '';
    username: string = '';

    onSave() {
        this.saveCampaign.emit({ campaign: this.campaign, username: this.username, keywords: this.keywordsInput });
    }

    onClose() {
        this.closeModal.emit();
    }

}
