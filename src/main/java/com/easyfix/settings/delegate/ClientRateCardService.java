package com.easyfix.settings.delegate;

import java.util.List;

import com.easyfix.settings.model.ClientRateCard;

public interface ClientRateCardService {
	
	public List<ClientRateCard> getClientRateCardList() throws Exception;
	public ClientRateCard getClientRateCardDetailsById(int clientRateCardId) throws Exception;
	public int addUpdateClientRateCard(ClientRateCard clientRateCardObj) throws Exception;
	public List<ClientRateCard> getRateCardListForClient(int clientId, int serviceTypeId) throws Exception;

}
