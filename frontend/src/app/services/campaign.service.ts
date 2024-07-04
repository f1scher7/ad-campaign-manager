import {Injectable} from '@angular/core';
import {HttpClient, HttpParams} from '@angular/common/http';
import {map, Observable} from 'rxjs';
import {CampaignModel} from '../models/campaign.model';

@Injectable({
    providedIn: 'root'
})
export class CampaignService {

    baseUrl = 'http://localhost:8080/api/campaigns';

    constructor(private http: HttpClient) {}

    getAllCampaigns(): Observable<CampaignModel[]> {
        return this.http.get<CampaignModel[]>(`${this.baseUrl}/all`).pipe(
            map(campaigns => {
                return campaigns.map(campaign => ({
                    id: campaign.id,
                    user: campaign.user,
                    campaignName: campaign.campaignName,
                    keywords: campaign.keywords,
                    bidAmount: campaign.bidAmount,
                    campaignFund: campaign.campaignFund,
                    campaignStatus: campaign.campaignStatus,
                    town: campaign.town,
                    radius: campaign.radius
                }));
            })
        );
    }

    createCampaign(campaignData: { campaign: CampaignModel, username: string, keywords: string }): Observable<CampaignModel> {
        const { campaign, username, keywords } = campaignData;

        const params = new HttpParams()
            .set('username', username)
            .set('keywords', keywords);

        return this.http.post<CampaignModel>(`${this.baseUrl}/create-campaign`, campaign, {
            headers: { 'Content-Type': 'application/json' },
            params: params
        });
    }

    updateCampaign(campaignData: { campaign: CampaignModel, keywords: string }): Observable<CampaignModel> {

        const { campaign, keywords } = campaignData;
        const { user, ...campaignWithoutUser } = campaign;

        const params = new HttpParams()
            .set('keywords', keywords);

        return this.http.put<CampaignModel>(`${this.baseUrl}/update-campaign`, campaignWithoutUser, {
            headers: { 'Content-Type': 'application/json' },
            params: params
        });
    }

    deleteCampaign(campaign: CampaignModel): Observable<void> {
        return this.http.delete<void>(`${this.baseUrl}/delete-by-id/${campaign.id}`);
    }

}
