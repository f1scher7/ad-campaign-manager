import { UserModel } from "./user.model";

export interface CampaignModel {
    id: number;
    user: UserModel;
    campaignName: string;
    keywords: string[];
    bidAmount: number;
    campaignFund: number;
    campaignStatus: boolean;
    town: string;
    radius: number;
}
