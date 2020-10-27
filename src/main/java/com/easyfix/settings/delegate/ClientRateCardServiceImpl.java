package com.easyfix.settings.delegate;

import java.util.List;

import com.easyfix.settings.dao.ClientRateCardDao;
import com.easyfix.settings.model.ClientRateCard;

public class ClientRateCardServiceImpl implements ClientRateCardService {
	
	private  ClientRateCardDao clientRateCardDao;

	public ClientRateCardDao getClientRateCardDao() {
		return clientRateCardDao;
	}

	public void setClientRateCardDao(ClientRateCardDao clientRateCardDao) {
		this.clientRateCardDao = clientRateCardDao;
	}

	@Override
	public List<ClientRateCard> getClientRateCardList() throws Exception {
		return clientRateCardDao.getClientRateCardList();
	}

	@Override
	public ClientRateCard getClientRateCardDetailsById(int clientRateCardId)
			throws Exception {
		return clientRateCardDao.getClientRateCardDetailsById(clientRateCardId);
	}

	@Override
	public int addUpdateClientRateCard(ClientRateCard clientRateCardObj)
			throws Exception {
		return clientRateCardDao.addUpdateClientRateCard(clientRateCardObj);
	}

	@Override
	public List<ClientRateCard> getRateCardListForClient(int clientId, int serviceTypeId) throws Exception {
		return clientRateCardDao.getRateCardListForClient(clientId,serviceTypeId);
	}

}
